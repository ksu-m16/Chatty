package chat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

class NetClient extends Thread implements IPublisher {
	private int udpPort;
	private int udpPortR;
	private int udpPortS;
	private String nickname;
	private InetAddress iaddress;
	public static final int s_BUFFER_SIZE = 1024;
	private DatagramSocket dsocket;
	private NetSender netSender;
	private NetReceiver netReceiver;
	private ConcurrentLinkedQueue<String> incoming = new ConcurrentLinkedQueue<String>();

	NetClient(String nickname, int udpPort, int udpPortR, InetAddress iaddress)
			throws IOException {
		this.nickname = nickname;
		this.udpPort = udpPort;
		this.udpPortR = udpPortR;
		this.iaddress = iaddress;
		dsocket = new DatagramSocket(udpPort);
		netSender = new NetSender(this, dsocket);
		netReceiver = new NetReceiver(this);
	}

	public NetClient(String nickname, int udpPort, int udpPortR, int udpPortS,
			InetAddress iaddress) throws IOException {
		this.nickname = nickname;
		this.udpPort = udpPort;
		this.udpPortR = udpPortR;
		this.udpPortS = udpPortS;
		this.iaddress = iaddress;
		dsocket = new DatagramSocket(udpPort);
		netSender = new NetSender(this, dsocket);
		netReceiver = new NetReceiver(this);
	}

	private LinkedList<IChatListener> clientListeners = new LinkedList<IChatListener>();

	public void addChatListener(IChatListener listener) {
		clientListeners.add(listener);
	}

	public void removeMyObjectListener(IChatListener listener) {
		int i = clientListeners.indexOf(listener);
		if (i >= 0)
			clientListeners.remove(i);
	}

	public void notifyListeners() {
		while (incoming.size() > 0) {
			String incomingMsg = incoming.poll();
			for (IChatListener listener : clientListeners) {
				listener.update(incomingMsg);
				System.out.println(incomingMsg + " " + listener.toString());
			}
		}
	}

	public void run() {
		Thread t = new Thread(netReceiver, "reader");
		t.setDaemon(true);
		t.start();
	}

	public void send(String message) throws IOException {
		netSender.send(message);
	}

	public void addToIncoming(String msg) {
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

	public InetAddress getAddress() {
		return iaddress;
	}

}