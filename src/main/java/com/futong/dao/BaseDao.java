package com.futong.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.bson.Document;

import com.futong.conn.DbConn;
import com.futong.conn.SSHConn;
import com.futong.domain.Host;
import com.futong.domain.LogFile;
import com.futong.server.SendServer;
import com.futong.utils.ConstantUtils;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClientURI;
import com.mongodb.QueryOperators;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * 操作数据库的工具类，主要负责对数据库 进行操作
 * @author went
 *
 */
public class BaseDao {
	
	public static void main(String[] args) {
		BaseDao dao = new BaseDao();
//		List<LogFile> ls = dao.getAllChangedLogFile();
		
//		System.out.println(ls.size());
//		dao.updateDb();
		long lastCount = dao.getLastCount("222", "bbb");
		System.out.println(lastCount);
	}
	
	private MongoDatabase db;
	private DbConn conn;
	




	public BaseDao() {
		conn = new DbConn("192.168.122.69","graylog");
		conn.connect();
		db = conn.getDatabase();
	}



	public List<Host> getAllHostAndLogFile() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 1、获得host列表
	 * 2、获得日志列表
	 * 3、增加host
	 * 4、增加日志
	 * 5、修改host
	 * 6、修改日志
	 * 7、删除host
	 * 8、删除日志
	 * 9、更新主机状态
	 * 10、更新日志状态
	 */
	//logState != 0;
	public List<LogFile> getAllChangedLogFile() {
		MongoCollection collection = this.db.getCollection(ConstantUtils.LOGFILE);
		BasicDBObject queryObject = new BasicDBObject().append("logState", new BasicDBObject().append(QueryOperators.NE, 0));
		MongoCursor coursor= collection.find(queryObject).iterator();
		List<LogFile> logFiles = new ArrayList<>(); 
		while(coursor.hasNext()){
			 Document d = (Document) coursor.next();
			 LogFile l = this.documentToLogFile(d);
			 logFiles.add(l);
		}
		 return logFiles;
	}
	
	/**
	 * 将所有日志的状态更新为未修改
	 */
	public void updateDb() {
		MongoCollection logfile = this.db.getCollection(ConstantUtils.LOGFILE);
		BasicDBObject filter = new BasicDBObject("logState",new BasicDBObject().append(QueryOperators.EXISTS,true));
		BasicDBObject updater = new BasicDBObject("logState",ConstantUtils.NOTCHANGE);
		BasicDBObject updateSetValue=new BasicDBObject("$set",updater);
		logfile.updateMany(filter, updateSetValue);
	}
	public List<Host> getAllHost() {
		MongoCollection host = this.db.getCollection(ConstantUtils.LOGHOST);
		MongoCursor rs = host.find().iterator();
		List<Host> hosts = new ArrayList<>();
		while(rs.hasNext()){
			Document d = (Document) rs.next();
			Host h = new Host();
			h.setHostname(d.getString("hostname"));
			h.setTypeName(d.getString("typeName"));
			h.setState(d.getInteger("state", 0));
			h.setUsername(d.getString("username"));
			h.setPassword(d.getString("password"));
			h.setPort(d.getInteger("port", 22));
			h.setIp(d.getString("ip"));
			hosts.add(h);
		}
		return hosts;
	}
	public long getLastCount(String hostIp, String logName) {
		MongoCollection logfile = this.db.getCollection(ConstantUtils.LOGFILE);
		BasicDBList condList = new BasicDBList(); 
		BasicDBObject condition1 = new BasicDBObject("hostIp",hostIp);
		BasicDBObject condition2 = new BasicDBObject("logName",logName);
		BasicDBObject condition3 = new BasicDBObject("lastCount",new BasicDBObject().append(QueryOperators.EXISTS, true));
		condList.add(condition1);
		condList.add(condition2);
		condList.add(condition3);  
		BasicDBObject filter = new BasicDBObject().append(QueryOperators.AND,condList);
		MongoCursor cur = logfile.find(filter).iterator();
		long lastCount = 0;
		while(cur.hasNext()){
			Document d = (Document) cur.next();
			lastCount = d.getLong("lastCount");
		}
		return lastCount;
	}
	public long getLastModify(String hostIp, String logName) {
		MongoCollection<Document> logfile = this.db.getCollection(ConstantUtils.LOGFILE);
		BasicDBList condList = new BasicDBList(); 
		BasicDBObject condition1 = new BasicDBObject("hostIp",hostIp);
		BasicDBObject condition2 = new BasicDBObject("logName",logName);
		BasicDBObject condition3 = new BasicDBObject("lastModify",new BasicDBObject().append(QueryOperators.EXISTS, true));
		condList.add(condition1);
		condList.add(condition2);
		condList.add(condition3);
		BasicDBObject filter = new BasicDBObject().append(QueryOperators.AND,condList);
		MongoCursor<Document> cur = logfile.find(filter).iterator();
		long lastModify = 0;
		while(cur.hasNext()){
			Document d = (Document) cur.next();
			lastModify = d.getLong("lastModify");
		}
		return lastModify;
	}
	public void updateCurr(String logName, String hostIp, long currCount, long currSize, long currentTimeMillis) {
		MongoCollection<Document> logfile = this.db.getCollection(ConstantUtils.LOGFILE);
		BasicDBList condList = new BasicDBList(); 
		BasicDBObject condition1 = new BasicDBObject("hostIp",hostIp);
		BasicDBObject condition2 = new BasicDBObject("logName",logName);
		condList.add(condition1);
		condList.add(condition2);
		BasicDBObject update = new BasicDBObject().append("lastCount",currCount)
													.append("lastSize",currSize)
													.append("lastModify",currentTimeMillis);
		BasicDBObject filter = new BasicDBObject().append(QueryOperators.AND,condList);
		BasicDBObject updateSetValue=new BasicDBObject().append("$set",update);
		logfile.updateMany(filter, updateSetValue);
	}
	
	//**********************工具方法************************
	private LogFile documentToLogFile(Document d) {
		LogFile l = new LogFile();
		l.setSender(null);
		l.setSshConn(null);
		l.setInterval(d.getInteger("interval", 60));
		l.setTotalCount(d.getInteger("totalCount", 0));
//		l.setLastStartTime(null);
		l.setLastModify(d.getLong("lastModify")==null ? 0l : d.getLong("lastModify"));
		l.setLastCount(d.getLong("lastCount")==null ? 0l :d.getLong("lastCount"));
		l.setLastSize(d.getLong("lastSize")== null ? 0l :d.getLong("lastSize"));
		l.setLogState(d.getInteger("logState", 0));
		l.setLogType(d.getString("logType")==null?"":d.getString("logType"));
		l.setLogName(d.getString("logName")==null?"":d.getString("logName"));
		l.setHostIp(d.getString("hostIp"));
		
		return l;
	}
	
	private Document logFileToDocument(LogFile f){
		Document doc = new Document();
		doc.put("hostIp",f.getHostIp());
		doc.put("logName", f.getLogName());
		doc.put("logType", f.getLogType());
		doc.put("logState", ConstantUtils.CHANGED);
		doc.put("lastSize", f.getLastSize());
		doc.put("lastCount",f.getLastCount());
		doc.put("lastModify", f.getLastModify() == 0l ? System.currentTimeMillis() : f.getLastModify());
		doc.put("firstStartTime", f.getFirstStartTime() == 0l ? System.currentTimeMillis() : f.getFirstStartTime());
		doc.put("lastStartTime",f.getLastStartTime());
		doc.put("totalCount", f.getTotalCount());
		doc.put("interval", f.getInterval());
		return doc;
		
	}
	
	private Document hostToDocument(Host h) {
		Document doc = new Document();
		doc.put("hostname",h.getHostname());
		doc.put("typeName",h.getTypeName());
		doc.put("state",ConstantUtils.CHANGED);
		doc.put("username",h.getUsername());
		doc.put("password",h.getPassword());
		doc.put("port",h.getPort());
		doc.put("ip",h.getIp());
		return doc;
	}
	
	//删除成功返回true
	public boolean deleteHost(String ip) throws Exception {
		boolean isSuccess = false;
		try {
			MongoCollection host = this.db.getCollection(ConstantUtils.LOGHOST);
			BasicDBObject filter = new BasicDBObject("ip",ip);
			host.deleteOne(filter);
			isSuccess = true;
		} catch (Exception e) {
			new Throwable("删除失败", e);
		}
		return isSuccess;
	}


	//根据hostIp查找logfiles
	public List<LogFile> getLogfilesByHostId(String hostIp) {
		List<LogFile> logfiles = new ArrayList<>();
		MongoCollection<Document> logfile = this.db.getCollection(ConstantUtils.LOGFILE);
		BasicDBObject condition = new BasicDBObject("hostIp",hostIp);
		BasicDBObject filter = new BasicDBObject(condition);
		MongoCursor<Document> cur = logfile.find(filter).iterator();
		while(cur.hasNext()){
			Document d = (Document) cur.next();
			logfiles.add(documentToLogFile(d));
		}
		return logfiles;
	}
	
	//新增logFile
	public boolean addLogfile(LogFile f) throws Exception{
		boolean isSuccess = true;
		MongoCollection<Document> logfile = this.db.getCollection(ConstantUtils.LOGFILE);
		Document doc = this.logFileToDocument(f);
		try {
			logfile.insertOne(doc);
		} catch (Exception e) {
			isSuccess = false;
			new Throwable("添加logfile失败",e);
		}
		return isSuccess;
	}
	
	public boolean updateLogFile(LogFile f) throws Exception {
		boolean isSuccess = true;
		MongoCollection<Document> logfile = this.db.getCollection(ConstantUtils.LOGFILE);
		Document doc = this.logFileToDocument(f);
		BasicDBObject filter1 = new BasicDBObject("hostIp", f.getHostIp());
		BasicDBObject filter2 = new BasicDBObject("logName", f.getLogName());
		BasicDBList condList = new BasicDBList(); 
		condList.add(filter1);
		condList.add(filter2);
		BasicDBObject condition = new BasicDBObject(QueryOperators.AND, condList);
		BasicDBObject updateSetValue=new BasicDBObject("$set",doc);
		try {
			logfile.updateOne(condition, updateSetValue);
		} catch (Exception e) {
			isSuccess = false;
			new Throwable("更新logfile失败",e);
		}
		return isSuccess;
		
	}
	
	/**
	 * 
	 * @param h
	 * @return 添加成功返回true
	 * @throws Exception
	 */
	public boolean addHost(Host h) throws Exception{
		boolean isSuccess = true;
		MongoCollection<Document> host = this.db.getCollection(ConstantUtils.LOGHOST);
		Document doc = this.hostToDocument(h);
		try {
			host.insertOne(doc);
		} catch (Exception e) {
			isSuccess = false;
			new Throwable("添加host失败",e);
		}
		return isSuccess;
	}


	/**
	 * 当前ip没有记录 返回true 
	 * @param ip
	 * @return
	 */
	public boolean checkUnique(String ip) {
		boolean isUnique = false;
		MongoCollection<Document> host = this.db.getCollection(ConstantUtils.LOGHOST);
		BasicDBObject condition1 = new BasicDBObject("ip",ip);
		MongoCursor<Document> cur = host.find(condition1).iterator();
		if(cur.tryNext() == null){
			isUnique = true;
		}
		return isUnique;
	}


	
	public boolean updateHost(Host h) throws Exception {
		boolean isSuccess = true;
		MongoCollection<Document> host = this.db.getCollection(ConstantUtils.LOGHOST);
		Document doc = this.hostToDocument(h);
		BasicDBObject filter = new BasicDBObject("ip", h.getIp());
		BasicDBObject updateSetValue=new BasicDBObject("$set",doc);
		try {
			host.updateOne(filter, updateSetValue);
		} catch (Exception e) {
			isSuccess = false;
			new Throwable("更新host失败",e);
		}
		return isSuccess;
		
	}



	public List<LogFile> getAllLogfiles() {
		MongoCollection logFile = this.db.getCollection(ConstantUtils.LOGFILE);
		MongoCursor rs = logFile.find().iterator();
		List<LogFile> logFiles = new ArrayList<>(); 
		while(rs.hasNext()){
			 Document d = (Document) rs.next();
			 LogFile l = this.documentToLogFile(d);
			 logFiles.add(l);
		}
		 return logFiles;
	}
	
	//检查是否存在两个
	public void checkDb() throws Exception{
		if(this.getAllLogfiles().size() == 0){
			LogFile f = new LogFile();
			f.setHostIp("default");
			f.setLogName("default");
			f.setLogType("default");
			//状态为没有改变，不会加入到调度队列
			this.addLogfile(f);
		}
		if(this.getAllHost().size() == 0){
			Host h = new Host();
			h.setHostname("localhost");
			h.setTypeName("default");
			h.setUsername("-");
			h.setPassword("-");
			h.setIp("default");
			this.addHost(h);
		}
	}


	

}
