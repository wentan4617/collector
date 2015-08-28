import java.net.InetAddress;
import java.net.UnknownHostException;


public class Iptest {
	public static void main(String[] args) {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String ip=addr.getHostAddress().toString();//获得本机IP
			System.out.println(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
