package chat.controller;

import java.io.IOException;
import java.util.List;

public interface IController {
	
	void sendMessage(String s);

	List<String> getHistory();

	void startChat() throws IOException;

	

}
