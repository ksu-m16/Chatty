package chat;

import java.util.ArrayList;

public class Chat {
	private String myMessage;
	private ArrayList<String> history;
	public String getMyMessage() {
		return myMessage;
	}
	public void setMyMessage(String myMessage) {
		this.myMessage = myMessage;
	}
	public ArrayList<String> getHistory() {
		return history;
	}
	public void addToHistory(String msg) {
		history.add(msg);
	}
	
//	history.add(txtFiled.getText());
//	txtrHistory.setText(history.get(0));
	
}
