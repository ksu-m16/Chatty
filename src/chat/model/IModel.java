package chat.model;

import java.io.IOException;
import java.util.List;

public interface IModel {

	public List<String> getHistory() throws IOException;

	// public List<MessageRecord> getHistory() throws IOException;

	public void startChat() throws IOException;

	public void sendMessage(String message);

	public void setNickname(String message);

}
