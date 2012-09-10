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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ChatModel implements IModel {

	private String pathToHistory = ".\\data2.txt";
	File f = new File(pathToHistory);

	// public void addMessageToFile(String message) throws IOException {
	public void addMessageToFile(MessageRecord msg) throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Gson gson = new GsonBuilder().create();
		OutputStreamWriter osw = new OutputStreamWriter(baos);
		gson.toJson(msg, osw);
		osw.flush();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String data = new String(baos.toByteArray());

		PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(f,
				true)));
		out1.println(data);
		out1.close();
	}
	
//	public void addMessageToFile(String serializedMsg) throws IOException {
//		
//		PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(f,
//				true)));
//		out1.println(serializedMsg);
//		out1.close();
//	}
	
	public List<String> getHistory() throws IOException {
		
		if (!f.exists()){
			f.createNewFile();
		}
			
		List<String> strMessages = new LinkedList<String>();
		List<MessageRecord> history = new LinkedList<MessageRecord>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(f)));
		while (reader.ready()) {
			InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(reader.readLine()
					.getBytes()));
			try{
				
				history.add((gson.fromJson(isr, MessageRecord.class)));
				}
				catch (com.google.gson.JsonSyntaxException e) {
					System.out.println("Unable to extract json ");
				}
		}

		for (MessageRecord r : history) {
			strMessages.add(r.toString());
		}
		reader.close();
		return strMessages;
	}
}
