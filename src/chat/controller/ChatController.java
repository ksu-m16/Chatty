package chat.controller;

import java.io.IOException;
import java.util.List;

import chat.model.IModel;
import chat.model.MessageRecord;


public class ChatController implements IController {

	private IModel model;

	public IModel getModel() {
		return model;
	}

	public void setModel(IModel model) {
		this.model = model;
	}

	@Override
	public void addMessage(String message) {
		try {
			model.addMessage(message);
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
	
	public void startChat() throws IOException{
		model.startChat();
	}

}
