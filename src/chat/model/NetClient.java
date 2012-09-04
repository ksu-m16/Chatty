package chat.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import chat.view.IChatListener;

public class NetClient extends Thread {
	private int udpPort;
	private int udpPortR;
	private int udpPortS;
	
	private ConcurrentLinkedQueue<String> incoming = new ConcurrentLinkedQueue<String>();

	private String nickname;
	private InetAddress iaddress;
	public static final int s_BUFFER_SIZE = 1024;

//	/**
//	 * InetAddress for MulticastSocket. All recipients will receive messages if
//	 * they will join this group address.
//	 */
	// public final InetAddress m_group = InetAddress.getByName("230.0.0.1");
//	private InetAddress iaddress = InetAddress.getByName("localhost");
	// private MulticastSocket m_socket; // UDP Socket to send messages
	private DatagramSocket dsocket;

	private NetSender netSender;
	private NetReceiver netReceiver;
	
//	public NetClient(String nickname) throws IOException {
//		dsocket = new DatagramSocket(udpPort);
//		netSender = new NetSender(this, dsocket);
//		netReceiver = new NetReceiver(this);
//		this.nickname = nickname;
//	}
	
	public NetClient(String nickname, int udpPort, int udpPortR, InetAddress iaddress) throws IOException {
		this.nickname = nickname;
		this.udpPort = udpPort;
		this.udpPortR = udpPortR;
		this.iaddress = iaddress;
		dsocket = new DatagramSocket(udpPort);
		netSender = new NetSender(this, dsocket);
		netReceiver = new NetReceiver(this);

	}
	
	public NetClient(String nickname, int udpPort, int udpPortR, 
			int udpPortS, InetAddress iaddress) throws IOException {
		this.nickname = nickname;
		this.udpPort = udpPort;
		this.udpPortR = udpPortR;
		this.udpPortS = udpPortS;
		this.iaddress = iaddress;
		dsocket = new DatagramSocket(udpPort);
		netSender = new NetSender(this, dsocket);
		netReceiver = new NetReceiver(this);

	}
	
	
LinkedList<IChatListener> listeners;
	
	public void addChatListener(IChatListener listener){
		listeners.add(listener);
	}
		
	public void removeMyObjectListener(IChatListener listener){
			int i = listeners.indexOf(listener);
	        if (i>=0) listeners.remove(i);
	}
	
	public void notifyListeners(){
		for (IChatListener listener : listeners){
			listener.update(incoming.poll());
        }
	}
	
	public void send(String message) throws IOException {
		netSender.send(message);
	}

	public void addToIncoming (String msg) {
		incoming.add(msg);
	}
	
	
	
	
	public int getUdpPort() {
		return udpPort;
	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}

	public int getUdpPortR() {
		return udpPortR;
	}

	public void setUdpPortR(int udpPortR) {
		this.udpPortR = udpPortR;
	}

	public int getUdpPortS() {
		return udpPortS;
	}

	public void setUdpPortS(int udpPortS) {
		this.udpPortS = udpPortS;
	}
	
	public void run() {
		Thread t = new Thread(netReceiver, "reader");
		t.setDaemon(true);
		t.start();

//		String line;
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		try {
//			while (true) {
//				try {
//					if ((line = in.readLine()) != null) {
//						netSender.send("qqqqqqqqqqqqqqqqqqqqqqq " + line);
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		} finally {
//			try {
//				in.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}

	
	// public InetAddress getGroupAddress() {
	// return iaddress;
	// }
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