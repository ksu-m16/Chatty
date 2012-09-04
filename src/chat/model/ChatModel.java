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

	File f = new File(".\\data2.txt");


	

//	public void addMessageToFile(String message) throws IOException {
	public void addMessageToFile(MessageRecord msg) throws IOException {
		History h = new History();
//		h.records.add(new MessageRecord(getCurrentTimestamp(), nickname,
//				message));
		h.records.add(msg);
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

	

	public List<String> getHistoryFromFile() throws IOException {
		List<String> strMessages = new LinkedList<String>();
		History hnew = new History();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(f)));
		while (reader.ready()) {
			hnew.deserialize(new ByteArrayInputStream(reader.readLine()
					.getBytes()));
		}

		for (MessageRecord r : hnew.records) {
			// System.out.println(r.toString());
			strMessages.add(r.toString());
		}
		reader.close();
		return strMessages;
	}

}
