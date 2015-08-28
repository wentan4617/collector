package com.futong.utils;

public class ConstantUtils {
	public static final String COLLECTTASKID = "collectTaskId";
	public static final String COLLECTGROUPID = "collectGroupId";
	public static final String COLLECTINTERVAL = "interval";
	
	//jobDataMap
	public static final String SSHCONN = "sshConn";
	public static final String SENDER = "sender";
	public static final String LASTCOUNT = "lastCount";
	public static final String LASTMODIFY = "lastModify";
	public static final String LOGNAME = "logName";
	public static final String LOGTYPE = "logType";
	
	//DB
	public static final String LOGFILE = "logFile";
	public static final String LOGHOST = "logHost";
	//state 
	//采集状态 0表示没改动，1表示有改动，2表示删除 3 表示新增日志类似
	public static final int	NOTCHANGE = 0;
	public static final int CHANGED = 1;
	public static final int DELETE = 2;
	public static final int NEW = 3;
	
	
}
