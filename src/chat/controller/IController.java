package chat.controller;

import java.io.IOException;
import java.util.List;

import chat.model.MessageRecord;
import chat.model.Settings;

public interface IController extends IPublisher {

	void sendMessage(String s);

	List<String> getHistory();

	void startChat() throws IOException;

	Settings getSettings();

	MessageRecord generateMessageRecord(String message);

}
