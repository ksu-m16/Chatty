package chat.controller;

import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import chat.model.MessageRecord;
import chat.model.Settings;

class NetClient extends Thread implements IPublisher, IChatListener {
	private Settings settings;
	private DatagramSocket dsocket;
	public static final int s_BUFFER_SIZE = 1024;
	private NetSender netSender;
	private NetReceiver netReceiver;
	private ConcurrentLinkedQueue<MessageRecord> incoming =
			new ConcurrentLinkedQueue<MessageRecord>();
	
	NetClient(Settings settings){
		this.settings = settings;
	}
	
	private void configure() throws SocketException, BindException {
		dsocket = new DatagramSocket(settings.getUdpPortR());
		
//		catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		netReceiver = new NetReceiver(dsocket);
//		netReceiver.setUdpPortR(settings.getUdpPortR());
		netReceiver.addChatListener(this);
		netSender = new NetSender(dsocket, settings.getUdpPortS(), settings.getAddress());
	}


//	public NetClient(String nickname, int udpPort, int udpPortR, int udpPortS,
//			InetAddress iaddress) throws IOException {
//		this.nickname = nickname;
//		this.udpPort = udpPort;
//		this.udpPortR = udpPortR;
//		this.udpPortS = udpPortS;
//		this.iaddress = iaddress;
//		dsocket = new DatagramSocket(udpPort);
//		netSender = new NetSender(this, dsocket);
//		netReceiver = new NetReceiver(this);
//	}

	private LinkedList<IChatListener> clientListeners = new LinkedList<IChatListener>();

	public void addChatListener(IChatListener listener) {
		clientListeners.add(listener);
	}

	public void removeChatListener(IChatListener listener) {
		int i = clientListeners.indexOf(listener);
		if (i >= 0)
			clientListeners.remove(i);
	}

	public void notifyListeners() {
		while (incoming.size() > 0) {
			MessageRecord incomingMsg = incoming.poll();
			for (IChatListener listener : clientListeners) {
				listener.update(incomingMsg);
				System.out.println(incomingMsg + " " + listener.toString());
			}
		}
	}

	public void run() {
		try {
			configure();
		} catch (BindException e) {
			// TODO Auto-generated catch block
			System.out.println("Port busy yet.Try another one.");
			return;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		Thread t = new Thread(netReceiver, "reader");
		t.setDaemon(true);
		t.start();
	}

	public void send(String message) throws IOException {
		netSender.send(message);
	}

	@Override
	public void update(MessageRecord incomingMsg) {
		incoming.add(incomingMsg);
		notifyListeners();
	}
	
}