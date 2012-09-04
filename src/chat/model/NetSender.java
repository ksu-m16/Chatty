package chat.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetSender {
	// private MulticastSocket m_socket;
	private DatagramSocket m_socket;
	private NetClient m_client;

	// public NetSender(NetClient client, MulticastSocket socket) {
	public NetSender(NetClient client, DatagramSocket socket) {
		m_client = client;
		m_socket = socket;
	}

	/**
	 * Send the given string to given address
	 * 
	 * @param string
	 * @param address
	 * @return true if sending successful, false otherwise
	 * @throws IOException
	 */
	public boolean send(String string, InetAddress address) throws IOException {
		boolean res = false;

		final byte buffer[] = new byte[NetClient.s_BUFFER_SIZE];
		int bLength = string.length() > NetClient.s_BUFFER_SIZE ? NetClient.s_BUFFER_SIZE
				: string.length();
		System.arraycopy(string.getBytes(), 0, buffer, 0, bLength);

		try {
			if (m_socket != null && !m_socket.isClosed()) {
				m_socket.send(new DatagramPacket(buffer, bLength, address,
						m_client.getUdpPortS()));

			}
			res = true;
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}

		return res;
	}

	/**
	 * Send the given string to all known recipients.
	 * 
	 * @param string
	 * @return true if sending successful, false otherwise
	 * @throws IOException
	 */
	public boolean send(String string) throws IOException {
		return send(string, m_client.getAddress());
	}
}