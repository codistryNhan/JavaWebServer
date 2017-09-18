package WebServer;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Iterator;

public class MimeTypes {
	private HashMap <String, String> types = new HashMap<String, String>();
	ConfigurationReader mimeType;
	Scanner mimeLine;
	
	/**
	 * Constructor of MimeTypes
	 * 
	 * @param fileName Path of file to be configured
	 */
	public MimeTypes(String fileName) {
		mimeType = new ConfigurationReader(fileName);
		//System.out.println("Successfully loaded mime.type File");
	}
	
	/**
	 * Loads mime.type file and stores in an HashMap
	 * 
	 */
	public void load() {
		System.out.println("-------Configuring Mime File-------");
		
		mimeType.load();
		addToMap();
		System.out.println("Mime File Configured\n\n");
	}
	
	/**
	 * Adds all lines from mime.type and puts in a HashMap
	 */
	public void addToMap() {
		Iterator<String>list = mimeType.options.iterator();
		while(list.hasNext()) {
			mimeLine = new Scanner(list.next()).useDelimiter("/");
			String type = mimeLine.next();
			String fileExtension = mimeLine.next();
			System.out.printf("Adding %s\n", fileExtension);
			types.put(fileExtension, type);
		}
		
		mimeLine.close();
	}
	
	/**
	 * Looks for the type of data to be used
	 * 
	 * @param extension Extension to be used
	 * @return type of date to be used
	 */
	public String lookup(String extension) {
		return types.get(extension);
	}
	
	
	
}
