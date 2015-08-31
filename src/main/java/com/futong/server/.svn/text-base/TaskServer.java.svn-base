package com.futong.server;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.futong.Task.LogCollecterTask;
import com.futong.Task.ScanDbTask;
import com.futong.domain.LogFile;
import com.futong.utils.ConstantUtils;



/**
 * 采集任务调度器
 * @author went
 *
 */
public class TaskServer {
	private static final Logger log = Logger.getLogger(TaskServer.class);
	
	private static TaskServer taskServer;
	
	
	private Scheduler scheduler;
	
	public static TaskServer getInstance(){
		if(taskServer == null){
			taskServer = new TaskServer();
		}
		return taskServer;
	}
	
	/**
	 * 启动任务调度服务器
	 */
	public void start() throws Exception {
		log.info("启动任务调度器");
		SchedulerFactory sf = new StdSchedulerFactory(); 
		try {
			scheduler = sf.getScheduler();
			scheduler.start();
			log.info("启动任务调度器成功");
		} catch (SchedulerException e) {
			log.error("启动任务调度器失败", e);
			throw new Exception("启动任务调度器失败", e);
		}
		
	}
	
	public void reStart() throws Exception {
		stop();
		start();
	}

	void stop() {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
	
	public void scheduleCollectTask(JobDataMap dataMap) throws Exception {
		
		String jobName = dataMap.getString(ConstantUtils.LOGNAME);
		String jobGroup = dataMap.getString(ConstantUtils.COLLECTGROUPID);
		int interval = dataMap.getInt(ConstantUtils.COLLECTINTERVAL);
		log.info("添加调度任务 ：" + jobGroup + "-" +jobName);
		JobDetail job = JobBuilder.newJob(LogCollecterTask.class)
				.withIdentity(jobName,jobGroup )
				.setJobData(dataMap)
				.build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(interval)
								.withMisfireHandlingInstructionIgnoreMisfires()
								.repeatForever())
				.withIdentity(jobName,jobGroup)
				.build();
			scheduler.scheduleJob( job,trigger);
	}
	
	public void scheduleScanDbTask(JobDataMap dataMap) throws Exception{
	}

	public void update(LogFile l) {
		this.delete(l);
		this.addLogCollectTask(l);
		
	}

	public void delete(LogFile l) {
		JobKey jobKey = new JobKey(l.getLogName(), l.getHostIp());
		try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}

	

	public void addScanDbTask() throws SchedulerException {
		log.info("将ScanDbTask加入任务调度器");
		JobDetail job = JobBuilder.newJob(ScanDbTask.class)
				.withIdentity("scanDbTask",Scheduler.DEFAULT_GROUP)
				.build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(60)
								.withMisfireHandlingInstructionIgnoreMisfires()
								.repeatForever())
				.withIdentity("scanDbTask",Scheduler.DEFAULT_GROUP)
				.build();
			scheduler.scheduleJob( job,trigger);// todo 是否考虑将相同周期、相同连接参数的任务编组执行
		log.info("当前ScanDbTask的名称是：scanDbTask 所属调度组是" + Scheduler.DEFAULT_GROUP);
	}

//	private LogCollecterTask logFileToTask(LogFile l) {
//		LogCollecterTask task = new LogCollecterTask();
//		
//		return task;
//	}
	
	public List<LogFile> getAllJobs(){
		List<LogFile> logfiles = new ArrayList<>();
		try {
			List<JobExecutionContext> lists = scheduler.getCurrentlyExecutingJobs();
			for(JobExecutionContext context : lists){
				LogFile f = new LogFile();
				JobDataMap map = context.getJobDetail().getJobDataMap();
				f.setLogName(map.getString(ConstantUtils.LOGNAME));
				f.setLogType(map.getString(ConstantUtils.LOGTYPE));
				f.setHostIp(map.getString(ConstantUtils.COLLECTGROUPID));
				f.setInterval(map.getInt(ConstantUtils.COLLECTINTERVAL));
				logfiles.add(f);
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logfiles;
	}
	/**
	 * 以下是对采集任务的控制
	 */
	
	public void addLogCollectTask(LogFile l) {
		JobDataMap dataMap = new JobDataMap();
		//采集需要的参数
		dataMap.put(ConstantUtils.SENDER, l.getSender());
		dataMap.put(ConstantUtils.SSHCONN, l.getSshConn());
		dataMap.put(ConstantUtils.LOGTYPE, l.getLogType());
		//都需要的参数
		dataMap.put(ConstantUtils.LOGNAME, l.getLogName());
		//Quartz需要的参数
		dataMap.put(ConstantUtils.COLLECTGROUPID, l.getHostIp());
		dataMap.put(ConstantUtils.COLLECTINTERVAL, l.getInterval());
		try {
			scheduleCollectTask(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
