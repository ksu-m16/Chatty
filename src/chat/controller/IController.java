package chat.controller;

import java.util.List;

public interface IController {
	void addMessage(String s);

	List<String> getHistory();

	void startChat();

}
