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
import chat.model.IModel;
import chat.model.MessageRecord;


import chat.model.Settings;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ChatController implements IController, IChatListener, IPublisher {

	private ChatModel model;
	private NetClient netClient;
	private MessageRecord incomingMsg;

	private Settings settings;
	
	public Settings getSettings() {
		return settings;
	}


	public void setControllerSettings(Settings settings) {
		this.settings = settings;
	}

	public void startChat() {
			netClient = new NetClient(settings);
			netClient.addChatListener(this);
			netClient.start();
			
	}

	@Override
	public void update(MessageRecord incomingMsg) {
		try {
			this.incomingMsg = incomingMsg;
			model.addMessageToFile(incomingMsg);
			notifyListeners();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<String> getHistory() {
		try {
			List<String> historyMessages = model.getHistory();
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
		MessageRecord msg = new MessageRecord(getCurrentTimestamp(), settings.getNickname(),
				message);
		return msg;
	}
	

	public void sendMessage(String message) {
		MessageRecord msg = generateMessageRecord(message);
		
		Gson gson = new GsonBuilder().create();
		String jsonMsg = gson.toJson(msg);
	
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

	public void removeChatListener(IChatListener listener) {
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


//	public NetClient getNetClient() {
//		return netClient;
//	}

	public ChatModel getModel() {
		return model;
	}

	public void setModel(ChatModel model) {
		this.model = model;
	}
}
