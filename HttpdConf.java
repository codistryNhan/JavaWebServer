import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class HttpdConf {

	private HashMap <String, String> aliases = new HashMap<String, String>();
	private HashMap <String, String> scriptAliases = new HashMap<String, String>();
	private HashMap <String, String> etc = new HashMap<String, String>();
	ConfigurationReader configureHttpd;
	Scanner httpdLine  = null;
	
	/**
	 * HttpdConf Constructor
	 * 
	 * @param fileName Path of file to be called
	 */
	public HttpdConf(String fileName){
		configureHttpd = new ConfigurationReader(fileName);
                this.load();
		//System.out.println("Successfully loaded httpd.conf file");
	}
	
	/**
	 * Adds configuration files from httpd.conf into HashMaps.
	 */
	public void addToMap() {
		Iterator<String> list = configureHttpd.options.iterator();
		String symbolicPath;
		
		while (list.hasNext()) {
			httpdLine = new Scanner(list.next());
			String configure;
			
			configure = httpdLine.next();
			System.out.printf("Adding %s\n", configure);
			
			if (configure.matches("Alias")) {
				symbolicPath = httpdLine.next();
				System.out.printf("Adding Alias. Symbolic Path: %s\n", symbolicPath);
				aliases.put(symbolicPath, httpdLine.next());
			}
			else if(configure.matches("ScriptAlias")) {
				symbolicPath = httpdLine.next();
				System.out.printf("Adding Script Alias. Symbolic Path: %s\n", symbolicPath);
				scriptAliases.put(symbolicPath, httpdLine.next());	
			}
			else {
				etc.put(configure, httpdLine.next());
			}
				
			
			
		}
		
		httpdLine.close();
	}
	
	/**
	 * Loads files from httpd.conf
	 */
	public void load(){
		System.out.println("-------Configuring Httpd File-------");
		
		configureHttpd.load();
		addToMap();
		
		System.out.println("Httpd File Configured\n\n");
	}
	
	/**
	 * Gets the URI of the server
	 * 
	 * @return Document path of index.html
	 */
        public String get(String key){
          return etc.get(key).replaceAll("^\"|\"$", "");
        }

        public String getAliasValue(String key){
          return aliases.get(key).replaceAll("^\"|\"$", "");
        }

        public boolean aliasKeyExist(String key){
          if(aliases.get(key) == null){
            return false;
          } else {
            return true;
          }
        }

        public String getScriptAliasValue(String key){
          return scriptAliases.get(key).replaceAll("^\"|\"$", "");
        }

        public boolean scriptAliasKeyExist(String key){
          if(scriptAliases.get(key) == null){
            return false;
          } else {
            return true;
          }
        }

}
