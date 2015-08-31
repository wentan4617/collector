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
		Long start = System.currentTimeMillis();
		String tName = Thread.currentThread().getName();
		log.info("ScanDbTask所属线程是 ："+tName);
		ProcessServer processServer = ProcessServer.getInstance();
		try {
			log.info("同步数据库开始时间是：" + start);
			processServer.start();
			log.info("同步数据库耗时：" + (System.currentTimeMillis() - start) + " 毫秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
