package mongodb;

import com.futong.dao.MongoConnection;
import com.futong.dao.MongoConnectionImpl;
import com.mongodb.Mongo;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongodbTest2 {
	public static void main(String[] args) {
		MongoConnection connection = new MongoConnectionImpl("192.168.122.69","graylog");
		connection.connect();
		MongoDatabase db = connection.getDatabase();
		db.getCollection("input");
	}
}
