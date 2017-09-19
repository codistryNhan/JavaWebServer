package WebServer;

import java.util.HashMap;
import java.util.stream.Stream;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Request {
	String uri;
	String body = "";
	String verb;
	String httpVersion = "HTTP/1.1";
	HashMap <String, String> headers = new HashMap <String, String>();
	HttpdConf currentHttpd;
	
	/**
	 * Constructor for Request
	 * 
	 * @param test Request to be parsed
	 * @param httpd The httpd.conf that is being used
	 */
	public Request(String test, HttpdConf httpd) {
		verb = test;
		currentHttpd = httpd;
	}
	
	public void initializeMap() {
		headers.put("User-Agent", "Mozilla/4.0");
		headers.put("Accept-Language", "en-us");
		headers.put("Connection", "Keep-Alive");
	}
	
	/*public Request(Stream client) {
		
	}
	*/
	
	/**
	 * Compile all the data needed based on Request Method
	 */
	public void parse(){
		if (verb.equalsIgnoreCase("GET")) {
			System.out.println(requestGET());
		}
	}
	
	/**
	 * GET Request Method
	 * 
	 * @return Verb, URI, HTTP Version, Headers, and bod 
	 */
	public String requestGET() {
		getBody();
		return String.format("%s %s %s\n"
				+ "%s\n"
				+ "%s\n", 
				verb, getURI(), httpVersion, getHeader(), body);
	}
	
	/**
	 * Gets body of html
	 */
	public void getBody() {
		File htmlFile = new File("C:/WebServer/public_html/index.html");
		BufferedReader htmlReader = null;
		String htmlBody = "";
		
		try {
			htmlReader = new BufferedReader(new FileReader(htmlFile));
			String bodyLine;
			while ((bodyLine = htmlReader.readLine()) != null) {
				htmlBody = htmlBody + bodyLine + "\n";
			}
		}
		catch(Exception e) {
			System.err.printf("HTML File not found: %s\n", e);
		}
		body = htmlBody;
	
	}
	
	/**
	 * Gets header of server
	 * 
	 * @return Header of server
	 */
	public String getHeader() {
		initializeMap();
		Set set = headers.entrySet();
		Iterator iterate = set.iterator();
		
		String compileHeader = "";

		while(iterate.hasNext()) {
			Map.Entry map = (Map.Entry)iterate.next();
			String newEntry = String.format("%s: %s\n", map.getKey(), map.getValue());
			compileHeader = compileHeader + newEntry;
		}
		
		return compileHeader;
	}
	
	/**
	 * Gets URI of server
	 * 
	 * @return URI
	 */
	public String getURI() {
		return currentHttpd.getURI();
	}
	
	
}
