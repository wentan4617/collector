package com.futong.server;

import java.net.InetSocketAddress;

import org.graylog2.gelfclient.GelfConfiguration;
import org.graylog2.gelfclient.GelfMessage;
import org.graylog2.gelfclient.GelfMessageBuilder;
import org.graylog2.gelfclient.GelfTransports;
import org.graylog2.gelfclient.transport.GelfTransport;

/**
 * 将日志发送给graylog2
 * @author went
 *
 */
public class SendServer {
	
	private static SendServer sendServer;
	
	public static SendServer getInstance(){
		if(sendServer == null){
			sendServer = new SendServer();
		}
		return sendServer;
		
	}
	
	private  GelfTransport transport;
	
	public synchronized GelfTransport getTransport(){
		return transport;
	}
	
	
	public void start() {
		GelfConfiguration config = new GelfConfiguration(new InetSocketAddress("192.168.122.69", 12201))
           .transport(GelfTransports.TCP)
           .queueSize(512)
           .connectTimeout(5000)
           .reconnectDelay(1000)
           .tcpNoDelay(true)
           .sendBufferSize(32768);

		transport = GelfTransports.create(config);
	}
	
	public GelfMessage getMessage(String msg,String hostIp,String logType){
		GelfMessage message = new GelfMessageBuilder(msg, hostIp).additionalField("logType", logType).build();
		return message;
	}


	public void stop() {
		// TODO Auto-generated method stub
		transport.stop();
		
	}

}
