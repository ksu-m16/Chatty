package chat.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import chat.model.ChatModel;
import chat.model.History;
import chat.model.IModel;
import chat.model.MessageRecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ChatController implements IController, IChatListener, IPublisher {

	private ChatModel model;
	private NetClient netClient;
	private String nickname;
	private InetAddress iaddress;
	private int udpPort;
	private int udpPortR;
	private int udpPortS;
	private String incomingMsg;

	public void startChat() throws IOException {
		try {
			// netClient = new NetClient(nickname, udpPort, udpPortR, iaddress);
			netClient = new NetClient(nickname, udpPort, udpPortR, udpPortS,
					iaddress);
			netClient.addChatListener(this);
			netClient.start();
			
		} catch (BindException e1) {
			System.out.println("Port busy yet. Try another one.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void update(String serializedIncomingMsg) {
		try {
			incomingMsg = serializedIncomingMsg;
//			model.addMessageToFile(new MessageRecord(incomingMsg));
			model.addMessageToFile("[" + serializedIncomingMsg + "]");
			notifyListeners();
			
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

	public MessageRecord generateMessageRecord(String message){
		MessageRecord msg = new MessageRecord(getCurrentTimestamp(), nickname,
				message);
		return msg;
	}
	
	public String getTextFromSerializedMsg(String serializedMsg){
		System.out.println("serializedmsg"+serializedMsg+"!");
		Gson gson = new Gson();
		
		MessageRecord msg = gson.fromJson((serializedMsg), MessageRecord.class);
		
		String strMessage = msg.toString();
		return strMessage;
	}
	
//	public void sendMessage(String message) {
//		MessageRecord msg = generateMessageRecord(message);
//		try {
//			netClient.send(msg.toString());
//			model.addMessageToFile(msg);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}


	public void sendMessage(String message) {
		MessageRecord msg = generateMessageRecord(message);
		
		Gson gson = new GsonBuilder().create();
		String jsonMsg = gson.toJson(msg);
		
		
//		History h = new History();
//		h.records.add(msg);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		try {
//			h.serialize(baos);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String data = new String(baos.toByteArray());
//		String data = new String(jsonMsg.toByteArray());
		try {
			netClient.send(jsonMsg);
			model.addMessageToFile(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	LinkedList<IChatListener> controllerListeners = new LinkedList<IChatListener>();
	public void addChatListener(IChatListener listener) {
		controllerListeners.add(listener);
	}

	public void removeMyObjectListener(IChatListener listener) {
		int i = controllerListeners.indexOf(listener);
		if (i >= 0)
			controllerListeners.remove(i);
	}

	public void notifyListeners() {
		for (IChatListener listener : controllerListeners) {
			listener.update(incomingMsg);
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

	public void setIAddress(String iaddress) {
		try {
			this.iaddress = InetAddress.getByName(iaddress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Unknown host");
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
