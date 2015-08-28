package com.futong.domain;

import java.io.Serializable;
import java.util.Date;

import com.futong.conn.SSHConn;
import com.futong.server.SendServer;



/**
 * 
 * @author went
 * @since 2015-5-11
 * @version v1.0
 * @description 日志文件
 */
public class LogFile{
	

	//该日志所属的主机
	private String hostIp;

	//logName日志名称就是日志的全路径
	private String logName;
	//TODO 这里需要一些静态的类型，待定
	//日志类型 大致分为已知类型（根据logName来判断属于那种类型）和未知类型
	private String logType;
	
	////采集状态 0表示没改动，1表示有改动，2表示删除 日志类似
	private int logState=0;
	
	//当前大小
	private long currentSize;
	//上次大小
	private long lastSize;
	
	//当前行数
	private long currentCount;
	//上次行数
	private long lastCount;
	
	//最后修改时间
	private long lastModify;

	//首次开始运行时间（在添加job时候赋值一次）
	private long firstStartTime;
	//最后一次执行时间（每次执行完了更新数据库）
	private long lastStartTime;
	
	//总共采集次数
	private int totalCount = 0;
	//轮询周期,默认60秒，可长不可短
	private int interval=60;
	
	private SSHConn sshConn ;
	
	private SendServer sender;
	
	

	public SSHConn getSshConn() {
		return sshConn;
	}

	public void setSshConn(SSHConn sshConn) {
		this.sshConn = sshConn;
	}


	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	
	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	
	public int getLogState() {
		return logState;
	}

	public void setLogState(int logState) {
		this.logState = logState;
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


	public long getFirstStartTime() {
		return firstStartTime;
	}

	public void setFirstStartTime(long firstStartTime) {
		this.firstStartTime = firstStartTime;
	}

	public long getLastStartTime() {
		return lastStartTime;
	}

	public void setLastStartTime(long lastStartTime) {
		this.lastStartTime = lastStartTime;
	}

	public SendServer getSender() {
		return sender;
	}

	public void setSender(SendServer sender) {
		this.sender = sender;
	}

	
	
	
	//TODO 需要解析器，缓存策略，压缩器，发送器。还是在job中定义，待定。
	
	

	
}
