package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyTask implements Job {
	private String name;
	public static int count = 0;
	


	public MyTask() {
		count ++;
	}




	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		name = context.getJobDetail().getJobDataMap().getString(Consistant.NAME);
		if(count == 2){
			try {
				int c = (count / 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		System.out.println(count);
	}

}
