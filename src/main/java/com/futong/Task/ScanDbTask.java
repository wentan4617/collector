package com.futong.Task;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.futong.server.ProcessServer;

/**
 * 定时扫描数据库任务
 * @author went
 *
 */
public class ScanDbTask implements Job {
	private static final Logger log = Logger.getLogger(ScanDbTask.class);
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String tName = Thread.currentThread().getName();
		log.info("线程 ："+tName + " 开始同步数据库");
		ProcessServer processServer = ProcessServer.getInstance();
		try {
			processServer.start();
			log.info("线程 ："+tName + " 同步数据库完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
