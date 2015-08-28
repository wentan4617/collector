package com.futong.conn;

import java.io.IOException;

import ch.ethz.ssh2.Connection;

import com.futong.domain.Host;

/**
 * 远程主机连接
 * @author went
 *
 */
public class SSHConn {
	
	private Connection conn;
	private boolean isconn = false;
	private String ip;
	private int port;
	private String username;
	private String password;
	public SSHConn(Host host) {
		ip = host.getIp();
		port = host.getPort();
		username = host.getUsername();
		password = host.getPassword();
		
		conn = new Connection(ip, port);
		
		
		try {
			conn.connect();
			isconn = conn.authenticateWithPassword(username, password);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		if(!isconn){
			try {
				conn.connect();
				isconn = conn.authenticateWithPassword(username, password);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	
	
}
