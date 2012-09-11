package chat;

import java.io.IOException;
import java.net.BindException;
import java.net.SocketException;

import javax.swing.JOptionPane;

import chat.controller.ChatController;
import chat.model.ChatModel;
import chat.model.IModel;
import chat.model.Settings;
import chat.view.ChatSettingsDialog;
import chat.view.ChatView;

public class Application {

	/**
	 * @param args
	 */
	ChatModel m;
	ChatView v;
	ChatSettingsDialog d;
	ChatController c;

	void init() {

		m = new ChatModel();
		v = new ChatView();
		d = new ChatSettingsDialog(v, true);
		c = new ChatController();
		c.setModel(m);
		v.setController(c);
	}

	// configure MVC relations with respect to user-defined parameters
	void configure() {
		c.setControllerSettings(d.getSettings());
		v.initialize();

	}

	void run() {
		init();
		d.showSettingsDialog();

		if (!d.isOkbuttonPressed()) {
			System.exit(0);
		}
		configure();
		try {
			c.startChat();
		} catch (BindException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(v, "Port busy yet. Try another one.",
					"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(v, "Unable to start chat.", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		v.showChatView();
	}

	public static void main(String[] args) {
		Application app = new Application();
		app.run();
	}

}
