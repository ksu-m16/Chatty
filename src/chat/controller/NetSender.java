package chat.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

class NetSender {

	private DatagramSocket dgSocket;
	private int udpPortS;
	private InetAddress iaddress;

	NetSender(DatagramSocket socket, int udpPortS, String address) {
		this.dgSocket = socket;
		this.udpPortS = udpPortS;

		try {
			iaddress = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Unknown host");
			e.printStackTrace();
		}
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
			if (dgSocket != null && !dgSocket.isClosed()) {
				dgSocket.send(new DatagramPacket(buffer, bLength, address,
						udpPortS));

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
		return send(string, iaddress);
	}

	// public void setUdpPortS(int udpPortS) {
	// this.udpPortS = udpPortS;
	// }
}