package chat;

import chat.controller.ChatController;
import chat.model.ChatModel;
import chat.model.IModel;
import chat.view.ChatView;

public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IModel m = new ChatModel();
		ChatView v = new ChatView();
		ChatController c = new ChatController();
		c.setModel(m);
		v.setController(c);
		v.run();
	}

}
