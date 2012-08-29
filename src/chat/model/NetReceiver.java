package chat.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.SocketException;

public class NetReceiver implements Runnable {

	private NetClient m_client;

	public NetReceiver(NetClient client) {
		m_client = client;
	}

	public void run() {
//		MulticastSocket socket = null;
		DatagramSocket socket = null;
		try {
			// Create the multicast socket to send messages to the net-group.
//			socket = new MulticastSocket(NetClient.s_UDP_PORT_R);
			socket = new DatagramSocket(NetClient.s_UDP_PORT_R);

//			socket.joinGroup(m_client.getGroupAddress());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Receiver didn't start.");
			return;
		}

		while (true) {
			try {
				// Read datagram, transform its data to the string and print it.
				byte[] buffer = new byte[NetClient.s_BUFFER_SIZE];
				DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length);
				socket.receive(packet);

				System.out.println(new String(packet.getData(), 0, packet
						.getLength()));

				// Reset the length of the packet before reusing it.
				packet.setLength(buffer.length);
			} catch (IOException ex) {
				System.out
						.println("Some exception while reading the income stream");
			}
		}
	}
}
