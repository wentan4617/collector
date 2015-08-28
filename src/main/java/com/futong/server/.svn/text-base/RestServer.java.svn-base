package com.futong.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import com.futong.resource.CollectorResource;

public class RestServer {
	 private int port = 8888;
	 private URI BASE_URI;
	 private static RestServer restServer;
	 private HttpServer server;
	 
	 public static RestServer getInstance(){
		 if(restServer != null){
			 return restServer;
		 }
		 return new RestServer();
	 }
	 private RestServer(){
		try {
			//自动获取当前主机ip
			InetAddress addr = InetAddress.getLocalHost();
			String ip=addr.getHostAddress().toString();//自动获得本机IP
			BASE_URI = URI.create("http://"+ip+":"+port+"/");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		 
	 }
	 
	 public void start() {
	        try {
	            Map<String, String> initParams = new HashMap<String, String>();
	            initParams.put(ServerProperties.PROVIDER_PACKAGES,  CollectorResource.class.getPackage().getName());
	            System.out.println(BASE_URI);
	            server = GrizzlyWebContainerFactory.create(BASE_URI, ServletContainer.class, initParams);
	        } catch (IOException ex) {
	            
	      }
	  }
	 public void stop(){
		 server.shutdownNow();
	 }
	 public static void main(String[] args) {
		 RestServer server = RestServer.getInstance();
		 server.start();
		 try {
			System.in.read();
			server.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
