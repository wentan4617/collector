package com.futong.domain;


public class JobInfo {
	//日志的name
	private String jobName;
	private long firetime;
	private long jobRuntime;
	//日志的host ip
	private String host;
	//当前大小
	private long currentSize;
		//上次大小
	private long lastSize;
		
		//当前行数
	private long currentCount;
		//上次行数
	private long lastCount;
		
		//上次修改时间
	private long lastModify;
	
	//本次任务是否完成。只有为true时，该JobInfo才是有效的。
	private boolean isFinished = false;
	
	
	public boolean isFinished() {
		return isFinished;
	}
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	
	
	public long getFiretime() {
		return firetime;
	}
	public void setFiretime(long firetime) {
		this.firetime = firetime;
	}
	public long getJobRuntime() {
		return jobRuntime;
	}
	public void setJobRuntime(long jobRuntime) {
		this.jobRuntime = jobRuntime;
	}
	public String getJobName() {
		return jobName;
	}
	public String getHost() {
		return host;
	}
	
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public long getCurrentSize() {
		return currentSize;
	}
	public void setCurrentSize(long currentSize) {
		this.currentSize = currentSize;
	}
	public long getLastSize() {
		return lastSize;
	}
	public void setLastSize(long lastSize) {
		this.lastSize = lastSize;
	}
	public long getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(long currentCount) {
		this.currentCount = currentCount;
	}
	public long getLastCount() {
		return lastCount;
	}
	public void setLastCount(long lastCount) {
		this.lastCount = lastCount;
	}
	public long getLastModify() {
		return lastModify;
	}
	public void setLastModify(long lastModify) {
		this.lastModify = lastModify;
	}
	
}
