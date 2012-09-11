package chat.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;

import chat.model.MessageRecord;

class NetReceiver implements Runnable, IPublisher {
	DatagramSocket socket;
	Gson gson = new Gson();

	public NetReceiver(DatagramSocket socket) {
		this.socket = socket;
	}
	// private int udpPortR;
	private ConcurrentLinkedQueue<MessageRecord> incoming = new ConcurrentLinkedQueue<MessageRecord>();

	private LinkedList<IChatListener> receiverListeners = new LinkedList<IChatListener>();

	public void addChatListener(IChatListener listener) {
		receiverListeners.add(listener);
	}

	public void removeChatListener(IChatListener listener) {
		int i = receiverListeners.indexOf(listener);
		if (i >= 0)
			receiverListeners.remove(i);
	}

	public void notifyListeners() {
		while (incoming.size() > 0) {
			MessageRecord incomingMsg = incoming.poll();
			for (IChatListener listener : receiverListeners) {
				listener.update(incomingMsg);
			}
		}
	}

	public void run() {
		while (true) {
			try {
				// Read datagram, transform its data to the string and print it.
				byte[] buffer = new byte[NetClient.s_BUFFER_SIZE];

				DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length);
				socket.receive(packet);

				String msg = new String(packet.getData(), 0, packet.getLength());
				MessageRecord msgRecord = gson.fromJson(msg,
						MessageRecord.class);

				incoming.add(msgRecord);

				notifyListeners();

				// Reset the length of the packet before reusing it.
				packet.setLength(buffer.length);
			} catch (IOException ex) {
				System.out
						.println("Some exception while reading the income stream");
			}
		}
	}

}
