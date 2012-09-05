package chat.controller;

public interface IPublisher {
	public void addChatListener(IChatListener listener) ;
	public void removeMyObjectListener(IChatListener listener);
	public void notifyListeners();
}
