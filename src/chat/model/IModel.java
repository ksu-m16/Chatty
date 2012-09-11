package chat.model;

import java.io.IOException;
import java.util.List;

public interface IModel {

	public List<String> getHistory() throws IOException;

	public void addMessageToFile(MessageRecord incomingMsg) throws IOException;

}
