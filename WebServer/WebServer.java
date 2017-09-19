package WebServer;

public class WebServer {
	String serverName;
	HttpdConf httpd = new HttpdConf("C:\\WebServer\\conf\\httpd.conf");
	MimeTypes mime = new MimeTypes("C:\\WebServer\\conf\\mime.types");
	
	/**
	 * Creates a new web server object
	 * 
	 * @param serverName Name of the server
	 */
	public WebServer(String serverName) {
		this.serverName = serverName;
	}
	
	
	/**
	 * Starts web server
	 */
	public void start() {
		httpd.load();
		
		mime.load();
	}
	
	/**
	 * Responds to request 
	 * 
	 * @param newRequest Request method to be called
	 */
	public void Request(String newRequest) {
		Request requestConf = new Request(newRequest, httpd);
		requestConf.parse();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebServer firstServer = new WebServer("firstServer");
		firstServer.start();
		
		firstServer.Request("GET");
	}

}
