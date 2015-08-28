package com.futong.server;

import org.apache.log4j.Logger;


/**
 * 服务管理者，负责服务的启停。
 * @author went
 *
 */
public class ServerManager {
	private static final Logger log = Logger.getLogger(ServerManager.class);
	private TaskServer taskServer;
	private SendServer sendServer;
	private RestServer restServer; //新增加的RestFul 服务
	
	public ServerManager() {
		taskServer = TaskServer.getInstance();
		sendServer = SendServer.getInstance();
		restServer = RestServer.getInstance();
	}
	/**
	 * 启动所有服务
	 * taskServer
	 * SendServer
	 * restServer
	 */
	public void startAllServer() throws Exception {
		try {
			taskServer.start();
			sendServer.start();
			restServer.start();
			
		} catch (Exception e) {
			log.error("启动服务失败",e);
			throw new Exception("服务启动异常",e);
		}
		
		
	}
	public void stopAllServer() {
		
		this.taskServer.stop();
		this.sendServer.stop();
		this.restServer.stop();
		
	}

}
