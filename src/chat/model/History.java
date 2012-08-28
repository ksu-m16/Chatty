package chat.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class History {
public List<MessageRecord> records = new LinkedList<MessageRecord>();
	
	public void serialize(OutputStream out) throws IOException {
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Gson gson = new GsonBuilder().create();
		OutputStreamWriter osw = new OutputStreamWriter(out);
		gson.toJson(records.toArray(), osw);
		osw.flush();		
	}
	
	public void deserialize(InputStream in) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		InputStreamReader isr = new InputStreamReader(in);			
//		records = new LinkedList<MessageRecord>(Arrays.asList(gson.fromJson(isr, MessageRecord[].class)));	
		records.add((Arrays.asList(gson.fromJson(isr, MessageRecord[].class))).get(0));
	}
}
