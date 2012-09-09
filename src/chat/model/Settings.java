package chat.model;

import java.net.InetAddress;

public class Settings {
	
	private int udpPortR;
	private int udpPortS;
	private String nickname;
	private String address;
	
//	public Settings(int udpPort, int udpPortR, int udpPortS, String nickname,
//			String address) {
//		this.address = address;
//		this.nickname = nickname;
//		this.udpPort = udpPort;
//		this.udpPortR = udpPortR;
//		this.udpPortS = udpPortS;
//	}
	
	@Override
	public String toString() {
		return "Settings [udpPortR=" + udpPortR
				+ ", udpPortS=" + udpPortS + ", nickname=" + nickname
				+ ", address=" + address + "]";
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
