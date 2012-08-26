package chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MyModel implements IModel {
	File f = new File(".\\data.txt");
	
	public List<String> getHistory() throws IOException{
		List<String> history = new ArrayList<String>();
		history.add("");		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		while(reader.ready()){
			history.add(reader.readLine());
		}
		reader.close();
		return history;	
	}
	
	public void addMessage(String message){
		
	}
	
	public void setNumber(int n) throws IOException{
		PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(f)));
		out1.print(n);
		out1.close();
	}
	
}
