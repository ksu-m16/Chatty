package chat;

import java.io.IOException;

import chat.controller.ChatController;
import chat.model.ChatModel;
import chat.model.IModel;
import chat.view.ChatView;

public class Application {

	/**
	 * @param args
	 */
	ChatModel m;
	ChatView v;
	ChatController c;

	private void configureApp() {
		m = new ChatModel();
		v = new ChatView();
		c = new ChatController();
		c.setModel(m);
		v.setController(c);
		// c.getNetClient().addChatListener(c);
		// c.getNetClient().addChatListener(v);

	}

	public static void main(String[] args) {
		Application app = new Application();
		app.configureApp();
		// try {
		// app.runApp();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// IModel m = new ChatModel();
		// ChatView v = new ChatView();
		// ChatController c = new ChatController();
		// c.setModel(m);
		// v.setController(c);
		app.v.run();

	}
	//
	// public void runApp() throws IOException {
	// // Thread viewThread = new Thread(v, "viev");
	// // viewThread.start();
	// // Thread netThread = new Thread();
	// // netThread.start();
	// // t.setDaemon(true);
	//
	// }

}
