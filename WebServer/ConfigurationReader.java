import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ConfigurationReader {
	File file;
	private Scanner reader;
	ArrayList<String> options = new ArrayList<String>();
	private String line;
	Scanner scanLine = null;
	
	/**
	 * Reads file and uses a text scanner
	 * 
	 * @param fileName  Path of the file to be scanned
	 */
	public ConfigurationReader(String fileName){
		this.file = new File(fileName);	
		try {
			this.reader = new Scanner(file);
		}
		catch(Exception e) {
			System.err.printf("File Not found: %s\n", e);
		}
		
		
	}
	
	/**
	 * Checks if the file has more lines
	 * 
	 * @return true   If files have more lines
	 * @return false  If files does not have more lines
	 */
	public boolean hasMoreLines() {
		if (reader.hasNextLine()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Gets the next line of file
	 * 
	 * @return String of the file line
	 */
	public String nextLine() {
		return line;
	}
	
	/**
	 * Loads the line and adds it in an arrayList. Closes scanner.
	 */
	public void load() {
		while (hasMoreLines()) {
			if(!isComment()) {
				if (scanLine.hasNext()) {
					String addToArray = scanLine.nextLine();
					options.add(addToArray);
				
				}
			}
		}
		scanLine.close();
		
	}
	
	/**
	 * Checks if current line is a comment.
	 * 
	 * @return false If it starts with '#'
	 * @return true If line doesn't start with '#'                                                                                          
	 */
	public boolean isComment(){
		line = reader.nextLine();
		scanLine = new Scanner(line);
		if (line.startsWith("#")) {
			return true;
		}
		
		return false;
	}
}
