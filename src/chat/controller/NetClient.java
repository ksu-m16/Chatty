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
	private ConcurrentLinkedQueue<MessageRecord> incoming = new ConcurrentLinkedQueue<MessageRecord>();

	NetClient(Settings settings) {
		this.settings = settings;
	}

	public void configure() throws SocketException, BindException {
		dsocket = new DatagramSocket(settings.getUdpPortR());
		netReceiver = new NetReceiver(dsocket);
		netReceiver.addChatListener(this);
		netSender = new NetSender(dsocket, settings.getUdpPortS(),
				settings.getAddress());
	}

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

	@Override
	public void update(MessageRecord incomingMsg) {
		incoming.add(incomingMsg);
		notifyListeners();
	}

}