package com.futong.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.futong.conn.SSHConn;
import com.futong.dao.BaseDao;
import com.futong.domain.Host;
import com.futong.domain.LogFile;
import com.futong.utils.ConstantUtils;

//数据库同步流程控制类
public class ProcessServer {
	private static Logger log = Logger.getLogger(Process.class);
	private static ProcessServer processServer;
	
	public static ProcessServer getInstance(){
		if(processServer == null){
			processServer = new ProcessServer();
		}
		return processServer;
	}
	private BaseDao dao;
	
	private ProcessServer() {
		// TODO Auto-generated constructor stub
		dao = new BaseDao();
	}


	//缓存的主机列表
	private TaskServer taskServer = TaskServer.getInstance();
	
	//缓存所有主机的SSHConn
	private Map<String,SSHConn> sshConnMap = new HashMap<String, SSHConn>();
	
	private SendServer sender = SendServer.getInstance();
	
	//采集程序流程入口
	public void start() throws Exception{
		
		//1查询数据库获得所有host以及所属log
		List<LogFile> logFiles = this.getAllChangedLogFile();
		List<Host> hosts = this.getAllHost();
		log.info("1查询数据库获得所有host以及所属log 其中host有 : "+hosts.size()+"个，logfile有 ："+logFiles.size()+"个");
		//
		if(hosts != null && hosts.size() >0 ){
			init(hosts);
		}else{
			throw new Exception("没有主机");
		}
		
		
		//2更新数据库，将所有状态改为没有改动 
		log.info("2更新数据库，将所有状态改为没有改动");
		this.updateDb();
		//3根据状态更新任务
		this.updateSchedule(logFiles);
		
//		this.taskServer.addScanDbTask();
		
	}
	
	
	private void init(List<Host> hosts) {
		for(Host host : hosts){
			//验证host是否可用状态，将删除的host删除
			if(updateHostState(host)){
				continue;//结束本次循环，进行下一次。
			}
			String ip = host.getIp();
			if(!sshConnMap.containsKey(ip)){
				SSHConn conn = new SSHConn(host);
				sshConnMap.put(ip, conn);
			}
		}
	}

	//host的state为2 删除时 为true
	private boolean updateHostState(Host host) {
		boolean flag = host.getState() == ConstantUtils.DELETE ? true : false;
		if(flag){
			try {
				log.info("主机 "+host.getIp() +" 的状态为删除");
				dao.deleteHost(host.getIp());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}


	private List<Host> getAllHost() {
		return dao.getAllHost();
	}


	private void updateDb() {
		// 1、锁住数据库 2、删除标记为删除的logFile 3、所有状态置为0
		dao.updateDb();
	}

	//查詢所有修改過的日誌
	private List<LogFile> getAllChangedLogFile() {
		
		return dao.getAllChangedLogFile();
	}




	private void updateSchedule(List<LogFile> logFiles) {
		//更改逻辑如下：有个标记位，0表示没改动，1表示有改动，2表示删除，3表示新增 日志类似
		//1、删除没有修改的内容
		for(LogFile l : logFiles){
			SSHConn conn = sshConnMap.get(l.getHostIp());
			l.setSender(sender);
			l.setSshConn(conn);
			if(l.getLogState() == ConstantUtils.CHANGED){
				//1表示有改动
				taskServer.update(l);
				
			}
			if(l.getLogState() == ConstantUtils.DELETE){
				//2表示删除
				taskServer.delete(l);
			}
			if(l.getLogState() == ConstantUtils.NEW){
				//3表示新增
				taskServer.addLogCollectTask(l);
			}
		}
		
		
	}




	
	private static <T> T compareObject(T t1,T t2){
		
		return t2;
		
	}
	public static void main(String[] args) {
		String s1 = "went";
		String s2 = "shuishui";
		String r = compareObject(s1,s2);
		System.out.println(r);
	}
}
