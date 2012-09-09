package chat.controller;

import chat.model.MessageRecord;

public interface IChatListener {
	public void update(MessageRecord incomingMsg);
}
