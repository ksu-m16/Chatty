package chat.controller;

import java.io.IOException;
import java.net.BindException;
import java.util.List;

import chat.model.ChatModel;
import chat.model.IModel;
import chat.model.MessageRecord;

public class ChatController implements IController {

	private ChatModel model;

	public ChatModel getModel() {
		return model;
	}

	public void setModel(ChatModel model) {
		this.model = model;
	}

	@Override
	public void sendMessage(String message) {
		model.sendMessage(message);
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

	public void startChat()  {
		try {
			model.startChat();
		} 
		catch (BindException e1) {
			System.out.println("Port busy yet. Try another one.");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setNickname(String nickname) {
		model.setNickname(nickname);
	}

	public void setUdpPort(int udpPort) {
		model.setUdpPort(udpPort);
	}
	
	public void setUdpPortR(int udpPortR) {
		model.setUdpPortR(udpPortR);
	}
	
	public void setUdpPortS(int udpPortS) {
		model.setUdpPortS(udpPortS);
	}
	
	public void setIAddress(String iaddress) {
		model.setIaddress(iaddress);
	}
}
