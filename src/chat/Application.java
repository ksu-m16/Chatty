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

	   void configure() {
		   c.setControllerSettings(d.getSettings());
		   v.initialize();
	      //configure MVC relations with respect to user-defined parameters
		   
	   }

	    void run() {
	         init();         
	         d.showSettingsDialog();
	         configure();
	         c.startChat();
			
	         if (d.isOkbuttonPressed()) {
	        	 v.showChatView();
	         }
	         try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	

	public static void main(String[] args) {
		Application app = new Application();
		app.run();
	}

}
