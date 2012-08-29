package chat.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class NetClient {
	public static final int s_UDP_PORT = 8888;
	public static final int s_UDP_PORT_R = 8889;

	public static final int s_BUFFER_SIZE = 1024;

	/**
	 * InetAddress for MulticastSocket. All recipients will receive messages if
	 * they will join this group address.
	 */
//	public final InetAddress m_group = InetAddress.getByName("230.0.0.1");
	public final InetAddress iaddress = InetAddress.getByName("localhost");
//	private MulticastSocket m_socket; // UDP Socket to send messages
	private DatagramSocket dsocket;
	
	private NetSender netSender;
	private NetReceiver netReceiver;

	/**
	 * User nickname
	 */
	private String nickname;

	public NetClient(String nickname) throws IOException {
		dsocket = new MulticastSocket(s_UDP_PORT);
		netSender = new NetSender(this, dsocket);
		netReceiver = new NetReceiver(this);
		this.nickname = nickname;
	}

	public void run() throws IOException {
		Thread t = new Thread(netReceiver, "reader");
		t.setDaemon(true);
		t.start();

//		String line;
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		try {
//			while (true) {
//				if ((line = in.readLine()) != null) {
//					netSender.send(nickname + ": " + line);
//				}
//			}
//		} finally {
//			in.close();
//		}
	}

	public void send(String message) throws IOException{
		netSender.send(message);
	}
	
//	public InetAddress getGroupAddress() {
//		return iaddress;
//	}
	public InetAddress getAddress() {
		return iaddress;
	}

	// public static void main(String[] args) throws IOException
	// {
	// if(args.length < 1)
	// {
	// System.out.println("usage: nickname is needed.");
	// System.exit(1);
	// }
	//
	// new NetClient(args[0]).run();
	// }
}