package chat;

import java.io.IOException;

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
	
	//configure MVC relations with respect to user-defined parameters
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
	         c.startChat();
	         v.showChatView();
	    }
	
	
	public static void main(String[] args) {
		Application app = new Application();
		app.run();
	}

}
