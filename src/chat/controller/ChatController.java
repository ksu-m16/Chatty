package chat.controller;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import chat.model.ChatModel;
import chat.model.IModel;
import chat.model.MessageRecord;
import chat.model.NetClient;
import chat.view.IChatListener;

public class ChatController implements IController, IChatListener {

	private ChatModel model;
	NetClient netClient;


	private String nickname;
	private InetAddress iaddress;
	private int udpPort;
	private int udpPortR;
	private int udpPortS;
	
	private String incomingMsg;
	
	
//	   protected void fireMessageReceived(String event) {
//	        listeners.update(event);
//	    }

	public void startChat() throws IOException {
		try{
//		netClient = new NetClient(nickname, udpPort, udpPortR, iaddress);
		netClient = new NetClient(nickname, udpPort, udpPortR, udpPortS, iaddress);
		netClient.start();
		}
		catch (BindException e1) {
			System.out.println("Port busy yet. Try another one.");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void update(String incomingMsg) {
		try {
			model.addMessageToFile(new MessageRecord(incomingMsg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

	@Override
	public List<String> getHistory() {
		try {
			List<String> historyMessages = model.getHistoryFromFile();
			if (historyMessages.size() == 0) {
				historyMessages.add("");
			}
			return historyMessages;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable to open file");
			return null;
		}
	}
	
	public void sendMessage(String message) {
		try {
			netClient.send(message);
			model.addMessageToFile(new MessageRecord(getCurrentTimestamp(), nickname,
				message));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getCurrentTimestamp() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public int getUdpPortR() {
		return udpPortR;
	}

	public void setUdpPortR(int udpPortR) {
		this.udpPortR = udpPortR;
	}

//	public String getIaddress() {
//		return iaddress;
//	}

	public void setIAddress(String iaddress) {
		try {
			this.iaddress = InetAddress.getByName(iaddress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println ("Unknown host");
			e.printStackTrace();
		}
	}

	public int getUdpPort() {
		return udpPort;
	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}
	
	public int getUdpPortS() {
		return udpPortS;
	}

	public void setUdpPortS(int udpPortS) {
		this.udpPortS = udpPortS;
	}

	
	public NetClient getNetClient() {
		return netClient;
	}
	public ChatModel getModel() {
		return model;
	}

	public void setModel(ChatModel model) {
		this.model = model;
	}
}
