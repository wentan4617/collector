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
		
		dao = new BaseDao();
	}


	
	private TaskServer taskServer = TaskServer.getInstance();
	
	//缓存所有主机的SSHConn
	private Map<String,SSHConn> sshConnMap = new HashMap<String, SSHConn>();
	
	private SendServer sender = SendServer.getInstance();
	
	//重启采集程序时运行一次初始化
		public void bootstrap() throws Exception{
			log.info("初始化采集机");
			//1、查询所有的logFile
			List<LogFile> logFiles = dao.getAllLogfiles();
			//2、获得所有主机列表
			List<Host> hosts = dao.getAllHost();
			//
			if(hosts != null && hosts.size() >0 ){
				init(hosts);
			}else{
				log.error("请先增加被采集主机");
				throw new Exception("还没有配置主机");
			}
			
			
			//2更新数据库，将所有状态改为没有改动 
			//TODO host状态没有改变，还没有想好。
			this.updateDb();
			//3将所有logFile加入调度
			this.bootStrapAllJobs(logFiles);
			//4将ScanDbTask加入调度
			this.taskServer.addScanDbTask();
			log.info("初始化采集机成功");
		}
	
	//采集程序流程入口
	public void start() throws Exception{
		log.info("同步流程启动");
		//1、查询所有状态是“更改”的logFile
		log.info("查询所有状态是“更改”的logFile");
		List<LogFile> logFiles = this.getAllChangedLogFile();
		//2、获得所有主机列表
		log.info("获得所有主机列表");
		List<Host> hosts = this.getAllHost();
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
		
		log.info("同步流程结束");
	}
	
	
	private void init(List<Host> hosts) {
		for(Host host : hosts){
			//验证host是否可用状态，将删除的host删除
			if(updateHostState(host)){
				continue;//结束本次循环，进行下一次。
			}
			String ip = host.getIp();
			if(!sshConnMap.containsKey(ip)){
				log.info("新增主机：" + ip);
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
				log.info("删除主机 "+host.getIp());
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

	/**
	 * 更新数据库，将所有主机以及logFile的状态改为没有改变
	 */
	private void updateDb() {
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
				log.info("更新调度任务 ：" + l.getHostIp()+":/"+l.getLogName());
				taskServer.update(l);
				
			}
			if(l.getLogState() == ConstantUtils.DELETE){
				//2表示删除
				log.info("删除调度任务 ：" + l.getHostIp()+":/"+l.getLogName());
				taskServer.delete(l);
			}
			if(l.getLogState() == ConstantUtils.NEW){
				//3表示新增
				log.info("新增调度任务 ：" + l.getHostIp()+":/"+l.getLogName());
				taskServer.addLogCollectTask(l);
			}
		}
		
	}
	private void bootStrapAllJobs(List<LogFile> logFiles) {
		for(LogFile l : logFiles){
			SSHConn conn = sshConnMap.get(l.getHostIp());
			if(conn != null){
				l.setSender(sender);
				l.setSshConn(conn);
				//3新增job
				log.info("初始化调度任务 ：" + l.getHostIp()+":/"+l.getLogName());
				taskServer.addLogCollectTask(l);
			}else{
				//TODO 逻辑上不存在这种情况。除非init方法出错。
				log.error("sshConnMap中没有响应的conn，请检查host初始化方法init");
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
