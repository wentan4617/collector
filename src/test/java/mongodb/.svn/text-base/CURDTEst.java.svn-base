package mongodb;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.futong.dao.BaseDao;
import com.futong.dao.MongoConnection;
import com.futong.dao.MongoConnectionImpl;
import com.futong.domain.Host;
import com.futong.domain.LogFile;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.operation.UpdateOperation;

public class CURDTEst {
	private static MongoCollection col;
	private static MongoCollection host;
	static {
		MongoConnection connection = new MongoConnectionImpl("192.168.122.69","graylog");
		connection.connect();
		MongoDatabase db = connection.getDatabase();
		col = db.getCollection("logFile");
		host = db.getCollection("logHost");
	}
	
	public static void main(String[] args) {
		
		
//		LogFile l = new LogFile();
//		l.setCurrentCount(1000l);
//		l.setCurrentSize(23l);
//		l.setLogName("c/a/d.log");
//		l.setLogType("c");
//		
//		Document doc = new Document();
		
//		dbObject.put("currentCount", l.getCurrentCount());
//		dbObject.put("currentSize", l.getCurrentSize());
//		dbObject.put("logName", l.getLogName());
//		dbObject.put("logType", l.getLogType());
//		DBObject  filter = new BasicDBObject();
//		filter.put("logName", "aaa");
//		DBObject  update=new BasicDBObject();  
//		update.put("logName", "bbb");
//		
//		col.updateOne(filter, update);
//		Field[] fields = LogFile.class.getFields();
//		Method[] ms = LogFile.class.getMethods();
//		for(Field f : fields){
//			String fieldName = f.getName();
//			
//			
//		}
//		for(Method m : ms){
//			if(m.getName().startsWith("get")){
//				try {
//				Object o = m.invoke(l);
//				if(o != null){
//					System.out.println(o);
//				}
//				} catch (IllegalAccessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IllegalArgumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//	
//		 BasicDBObject filter = new BasicDBObject("logName", "aaa");  
//	     BasicDBObject update = new BasicDBObject("logName", "struts_new");  
//         col.updateOne(filter, update);
		
//		findAll();
//		update();
//		insert();
//		insertField();
//		findTime();
		
//		insertHost();
//		findId();
		delete();
	}
	
	public static void findAll(){
		MongoCursor cur = col.find().iterator();
		while(cur.hasNext()){
			Document d = (Document) cur.next();
			System.out.println(d);
		}
	}
	
	public static void update(){
		
		BasicDBObject filter = new BasicDBObject("logName", "bbb");
		BasicDBObject update = new BasicDBObject("logName", "ccc").append("logType", "22");
		BasicDBObject updateSetValue=new BasicDBObject().append("$set",update);
		col.updateMany(filter, updateSetValue);
	}
	//新增document
	public static void insert(){
		Document document = new Document();
		document.put("logType", 2);
		col.insertOne(document);
	}
	//在原有的document上insert
	public static void insertField(){
		BasicDBObject filter = new BasicDBObject("logName", "bbb");
		
		BasicDBObject update = new BasicDBObject("time", new Date());
		BasicDBObject updateSetValue=new BasicDBObject("$set",update);
		col.updateOne(filter, updateSetValue);
	}
	
	public static void findTime(){
		BasicDBObject filter = new BasicDBObject("logName", "bbb");
		MongoCursor cur = col.find(filter).iterator();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
		while(cur.hasNext()){
			Document d = (Document) cur.next();
			long str = d.getLong("time");
//			String str = format.format(date);
			System.out.println(str);
		}
	}
	
	public static void insertHost(){
		Document d = new Document();
		/**
		 * 	//主机ID	
	private int hostId ;
	*/
	
		d.append("name", "cloudoors04");
		d.append("typeName", "LINUX");
		//采集状态 0表示没改动，1表示有改动，2表示删除 日志类似
		d.append("state", 0);
		d.append("username", "went");
		d.append("password", "wentan4617");
		d.append("port", 22);
		d.append("ip", "192.168.122.64");
		d.append("lastmodify", new Date());
		host.insertOne(d);
	}
	public static void findId(){
		MongoCursor res = host.find().iterator();
		while(res.hasNext()){
			Document d = (Document)res.next();
			ObjectId str = (ObjectId) d.get("_id");
			System.out.println(str.toString());
			System.out.println(str.toHexString());
		}
		
	}
	
	public static void delete(){
		
		BasicDBObject filter = new BasicDBObject("ip","192.168.122.64");
		
		col.deleteOne(filter);
	}
	
	
	
	
	
	
	
	
	
}
