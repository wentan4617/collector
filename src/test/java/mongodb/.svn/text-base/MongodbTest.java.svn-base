package mongodb;

import org.bson.BsonString;
import org.bson.Document;

import com.futong.dao.MongoConnection;
import com.futong.dao.MongoConnectionImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongodbTest {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		MongoConnection connection = new MongoConnectionImpl("192.168.122.69","graylog");
		connection.connect();
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		start = end;
		MongoDatabase db = connection.getDatabase();
		end = System.currentTimeMillis();
		System.out.println(end-start);
		start = end;
		MongoCollection c = db.getCollection("inputs");
		end = System.currentTimeMillis();
		System.out.println(end-start);
		start = end;
		BasicDBObject f = new BasicDBObject();
		f.append("configuration.max_chunk_size",65536);
		FindIterable itr = c.find(f);
		end = System.currentTimeMillis();
		System.out.println(end-start);
		start = end;
		MongoCursor cs = itr.iterator();
		while (cs.hasNext()) {
			Document d = (Document) cs.next();
			System.out.println(d);
		}
	}
}
