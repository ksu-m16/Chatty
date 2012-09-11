package chat.controller;

public interface IPublisher {
	public void addChatListener(IChatListener listener);

	public void removeChatListener(IChatListener listener);

}
