package quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzTest {
	
	
	public static void main(String[] args) throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory(); 
		Scheduler s = sf.getScheduler();
		
		JobDataMap map = new JobDataMap();
		map.put(Consistant.NAME, "went");
		JobDetail job1 = JobBuilder.newJob(MyTask.class)
				.withIdentity("job1", "g1")
				.setJobData(map).build();
		JobDataMap map2 = new JobDataMap();
		map2.put(Consistant.NAME, "shuishui");
		JobDetail job2 = JobBuilder.newJob(MyTask.class)
				.withIdentity("job2", "g1")
				.setJobData(map2).build();
		Trigger t1 = TriggerBuilder.newTrigger()
				.withIdentity("job1", "g1")
				.startNow()
				.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(10, 2))
				.build();
		Trigger t2 = TriggerBuilder.newTrigger()
				.withIdentity("job2", "g1")
				.startNow()
				.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(10, 5))
				.build();
		s.scheduleJob(job1, t1);
		s.scheduleJob(job2, t2);
		s.start();
	}
}