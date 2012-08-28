package chat.model;

import java.io.IOException;
import java.util.List;

public interface IModel {
	public List<String> getHistory() throws IOException;
//	public List<MessageRecord> getHistory() throws IOException;

	public void addMessage(String message) throws IOException;

	public void startChat() throws IOException;
}
