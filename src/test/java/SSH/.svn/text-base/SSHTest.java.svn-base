package SSH;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSHTest {
	
	private Connection conn;
	private Session ssh;
	private String hostname = "192.168.122.64";
	private String username = "went";
	private String password = "wentan4617";
	private int port = 22;
	private String cmd = "ls -l ";
	private BufferedReader buff;
	private String line;
	
	@Test
	public void getFirstFromRemoteHost(){
		long start = System.currentTimeMillis();
		//1创见一个连接
		conn = new Connection(hostname, port);
		long end = System.currentTimeMillis();
		System.out.println("new Connection :" + (end-start));
		start = end;
		
		try {
			// 2发起连接动作  
			conn.connect();
			end = System.currentTimeMillis();
			System.out.println("conn.connect() :" + (end-start));
			start = end;
			
			// 3权限验证
			boolean isconn = conn.authenticateWithPassword(username, password);
			end = System.currentTimeMillis();
			System.out.println("authenticate session  :" + (end-start));
			start = end;
			
			//4创见session
			ssh = conn.openSession();
			end = System.currentTimeMillis();
			System.out.println("openSession :" + (end-start));
			start = end;
			
			ssh.execCommand(cmd);
			end = System.currentTimeMillis();
			System.out.println("execute command :" + (end-start));
			start = end;
			InputStream is = new StreamGobbler(ssh.getStdout());
			buff = new BufferedReader(new InputStreamReader(is));
			while ((line = buff.readLine()) != null) {
				System.out.println(line);
			}
			end = System.currentTimeMillis();
			System.out.println("out put" + (end-start));
			start = end;
			ssh.close();
			end = System.currentTimeMillis();
			System.out.println("close session" + (end-start));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
