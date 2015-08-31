package com.futong.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.graylog2.gelfclient.transport.GelfTransport;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.futong.conn.SSHConn;
import com.futong.dao.BaseDao;
import com.futong.server.SendServer;
import com.futong.utils.ConstantUtils;
/**
 * 日志采集任务
 * @author went
 * 
 * 遗留问题：应该根据日志的logType来执行不同采集策略。
 *
 */
public class LogCollecterTask implements Job {
	private static final Logger log = Logger.getLogger(LogCollecterTask.class);
	private SSHConn conn;
	private BaseDao dao;
	//private LogParser parser; TODO 如果需要采集端进行解析，则配置不同的parser进行解析即可，应该另起线程解析，不要用采集线程。
	private SendServer sender;
	private String logName;
	private String logType;
	private long lastCount;
	private long lastModify;//暂时没用
	private String hostIp;
	
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		if(logName == null || logType == null || sender == null || conn == null || hostIp == null || dao == null){
			init(context);
		}
		log.info("采集任务：" + hostIp +"-"+logName +" 开始");
		this.lastCount = dao.getLastCount(hostIp,logName);
		String cmd_count_size = "wc -l -c " + logName;
		
		try {
			String result = execRemoteCommand(cmd_count_size,true);
			log.info("cmd_count_size 的值是：" + result);
			String[] arr = result.split(" ");
			long currCount = Long.parseLong(arr[2]);
			long currSize = Long.parseLong(arr[3]);
			dao.updateCurr(logName,hostIp,currCount,currSize,System.currentTimeMillis());
			long diff = currCount > lastCount ? currCount - lastCount : 0;
			
			String command_tail = "head -n " + currCount + " " + logName + " | tail -n " + diff;
			execRemoteCommand(command_tail,false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}


	private void init(JobExecutionContext context) {
		log.info("初始化采集参数");
		JobDataMap map = context.getJobDetail().getJobDataMap();
		this.logName = map.getString(ConstantUtils.LOGNAME);
		this.logType = map.getString(ConstantUtils.LOGTYPE);
		this.sender = (SendServer) map.get(ConstantUtils.SENDER);
		this.conn = (SSHConn) map.get(ConstantUtils.SSHCONN);
		this.hostIp = conn.getConn().getHostname();
		this.dao = new BaseDao();
	}
	
	public String execRemoteCommand(String command,boolean isReturn) {
		// 一个链接为一个session
		Session ssh = null;
		String line = null;
		BufferedReader buff = null;
		long begin = System.currentTimeMillis();
		int count = 0;
		try {
				ssh = conn.getConn().openSession();
				ssh.execCommand(command);
				InputStream is = new StreamGobbler(ssh.getStdout());
				buff = new BufferedReader(new InputStreamReader(is));
				if(!isReturn){
					GelfTransport transport = sender.getTransport();
					while ((line = buff.readLine()) != null) {
						//发送到graylog
						transport.send(sender.getMessage(line, hostIp,logType));
						count++;
						if(count%50000 == 0){
							System.out.println(count);
						}	
					}
				}else{
					while ((line = buff.readLine()) != null) {
						return line;
					}
				}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if(buff != null){
					buff.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			ssh.close();
			log.info("运行结束，关闭连接");
			long end = System.currentTimeMillis();
			log.info("运行时间为：" + (end - begin) + "毫秒"+"一共" + count + "行");
		}
		return line;
	}
}
