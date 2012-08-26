package chat;

public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		IModel m = new MyModel();
		ChatView v = new ChatView();
		MyController c = new MyController();
		c.setModel(m);
		v.setController(c);
		v.run();				
	}

}
