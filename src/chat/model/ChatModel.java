package chat.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ChatModel implements IModel {
	
	File f = new File(".\\data.txt");
	private String nickname = "Me";
	NetClient me;
	
	public void startChat() throws IOException {
		me = new NetClient(nickname);
		me.run();
	
	}
//
//	public List<String> getHistory() throws IOException {
//		List<String> history = new ArrayList<String>();
//		history.add("");
//		BufferedReader reader = new BufferedReader(new InputStreamReader(
//				new FileInputStream(f)));
//		while (reader.ready()) {
//			history.add(reader.readLine());
//		}
//		reader.close();
//		return history;
//	}
//
//	public void addMessage(String message) throws IOException {
//		PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(f,
//				true)));
//		out1.println("[" + getCurrentTimestamp() + "] " + message);
//		out1.close();
//	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	private String getCurrentTimestamp() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
	
	
	
	public void addMessage(String message) throws IOException {
		History h = new History();
		h.records.add(new MessageRecord(
				getCurrentTimestamp(), nickname, message));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		try {
			h.serialize(baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String data = new String(baos.toByteArray());
		
		PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(f,
				true)));
		out1.println(data);
		out1.close();
	}
	
	public void sendMessage(String message){
		
	}
	
	public List<String> getHistory() throws IOException {
		List<String> strMessages = new LinkedList<String>();
		History hnew = new History();
			
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(f)));
		while (reader.ready()) {
			hnew.deserialize(new ByteArrayInputStream(reader.readLine().getBytes()));
			for (MessageRecord r : hnew.records) {
				System.out.println(r.toString());
				strMessages.add(r.toString());
			}
		}
		reader.close();
		
		return strMessages;
	}
	

}
