
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.util.collection.ImmutableMultivaluedMap;

import com.futong.domain.Host;
import com.futong.domain.LogFile;
import com.futong.domain.LogFileRest;
import com.futong.domain.User;


public class RestClient {
	private static String URI = "http://127.0.0.1:8888/col/";
	private static String res = "";
	public static void main(String[] args) {
		RestClient c = new RestClient();
		c.updateLogFile();
		    
	}
	
	//添加logfile
	public void addLogfile(){
		res = "logfile";
		LogFileRest f = new LogFileRest();
		f.setHostIp("192.168.122.64");
		f.setLogName("/data/disc8/went/derby.log");
		f.setLogType("TXT");
		User u = new User();
		u.setUserName("abc");
		Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI+res);
        Response response = target.request()
                .buildPost(Entity.entity(f, MediaType.APPLICATION_JSON))
                .invoke();
        System.out.println(response.getStatus());
        response.close();
	}
	
	//添加host
		public void addHost(){
			res = "loghost";
			Host h = new Host();
			h.setHostname("64");
			h.setIp("192.168.122.64");
			h.setPassword("wentan4617");
			h.setUsername("went");
			Client client = ClientBuilder.newClient();
	        WebTarget target = client.target(URI+res);
	        Response response = target.request()
	                .buildPost(Entity.entity(h, MediaType.APPLICATION_JSON))
	                .invoke();
	        System.out.println(response.getStatus());
	        response.close();
		}
		
		//删除host
		public void deleteHost(){
			res = "loghost/192.168.122.64";
			Client client = ClientBuilder.newClient();
	        WebTarget target = client.target(URI+res);
//	        Response response = target.request()
//	                .buildPost(Entity.entity(h, MediaType.APPLICATION_JSON))
//	                .invoke();
	        Response response = target.request().delete();
	        System.out.println(response.getStatus());
	        response.close();
		}
		//修改host
		public void updateHost(){
			res = "loghost";
			Host h = new Host();
			h.setHostname("64");
			h.setState(1);
			h.setIp("192.168.122.64");
			h.setPassword("Cl0uD00rs!");
			h.setUsername("root");
			Client client = ClientBuilder.newClient();
	        WebTarget target = client.target(URI+res);
	        Response response = target.request()
	                .buildPut(Entity.entity(h, MediaType.APPLICATION_JSON))
	                .invoke();
	        System.out.println(response.getStatus());
	        response.close();
		}
		
		public void updateLogFile(){
			res = "logfile";
			LogFileRest f = new LogFileRest();
			f.setHostIp("192.168.122.64");
			f.setLogName("/data/disc8/went/derby.log");
			f.setLogType("BIN");
			f.setInterval(360);
			Client client = ClientBuilder.newClient();
	        WebTarget target = client.target(URI+res);
	        Response response = target.request()
	                .buildPut(Entity.entity(f, MediaType.APPLICATION_JSON))
	                .invoke();
	        System.out.println(response.getStatus());
	        response.close();
		}
}
