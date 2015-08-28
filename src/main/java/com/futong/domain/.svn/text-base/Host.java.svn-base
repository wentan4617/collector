package com.futong.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;



/**
 * 
 * @author went
 * @since 2015-5-11
 * @version v1.0
 * @description 主机
 */
public class Host implements Serializable{
	
	private static final long serialVersionUID = 134217728L;

	//主机ID	
//	private int hostId ;
	
	//主机hostname
	private String hostname;
	
	//主机类型 linux unix aix 默认是linux
	private String typeName = "LINUX";

	//采集状态 0表示没改动，1表示有改动，2表示删除 日志类似
	private int state=0;
	

	//所属的日志
//	private Set<LogFile> logSet = null;

	//错误码
//	private String erorCode;
	
	//以下是ssh链接所需要的信息
	private String username;
	
	private String password;
	
	//端口号
	private int port = 22;
	
	//ip
	private String ip="";
	
	// 如果是被采集机，则有所属的主机id，0表示是采集机 1表示是被采集机
//	private int parentId = 1;
	//如果是采集机，则有本机上负责采集的机器集合
//	private Set<Host> hostSet = null;
	
//	private Date lastmodify;
	
	
	public Host(String username, String password, String ip) {
		this.username = username;
		this.password = password;
		this.ip = ip;
	}
	public Host(){
		
	}
	

//	public int getHostId() {
//		return hostId;
//	}
//
//	public void setHostId(int hostId) {
//		this.hostId = hostId;
//	}

//
//	public int getParentId() {
//		return parentId;
//	}
//
//	public void setParentId(int parentId) {
//		this.parentId = parentId;
//	}

//	public Set<Host> getHostSet() {
//		return hostSet;
//	}
//
//	public void setHostSet(Set<Host> hostSet) {
//		this.hostSet = hostSet;
//	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
//	public Date getLastmodify() {
//		return lastmodify;
//	}
//	public void setLastmodify(Date lastmodify) {
//		this.lastmodify = lastmodify;
//	}
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

//	public String getErorCode() {
//		return erorCode;
//	}
//
//	public void setErorCode(String erorCode) {
//		this.erorCode = erorCode;
//	}

//	public Set<LogFile> getLogSet() {
//		return logSet;
//	}
//
//	public void setLogSet(Set<LogFile> logSet) {
//		this.logSet = logSet;
//	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

		

}
