package chat.model;



public class MessageRecord {

	public String nickname = "Unknown";
	public String message = "";
	public String timestamp = "";
	
	MessageRecord(String timestamp, String nickname, String message) {
		this.timestamp = timestamp;
		this.nickname = nickname;
		this.message = message;
	}
	
	@Override
	public String toString() {	
		return "[" + timestamp + "] " + nickname +  ": " + message; 
	}
}
