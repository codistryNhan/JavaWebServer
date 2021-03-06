import java.util.*;
import java.net.*;
import java.io.*;

//
//  Request Class handles incoming socket requests
//

public class Request{
  private String uri;
  private String verb;
  private String httpVersion;
  private String body = "";
  private HashMap<String, String> headers = new HashMap<String, String>();
  private String ipAddress;

  public Request(Socket input){

    this.ipAddress = input.getRemoteSocketAddress().toString();
    try{
      this.parse(input.getInputStream());
    } catch(Exception e){
      System.out.println("Error: " + e);
    }

  }

  //
  //  Parse and store request message's metadata
  //

  public void parse(InputStream input) throws IOException{

    //
    //  Parse and store the verb, uri, and httpVersion of the initial request line
    //

    BufferedReader in = new BufferedReader( new InputStreamReader(input) );
    String line = in.readLine();
    char[] buff;
    Scanner scan = new Scanner(line);

    //Scanner scan = new Scanner(input);

    this.verb = scan.next();
    this.uri = scan.next();
    this.httpVersion = scan.next();
    // scan.nextLine();

    //
    //  Parse and store headers
    //

    String key, value;

    while( (line = in.readLine()) != null){

      if(line.equals("")){
        break;
      }

      scan = new Scanner(line);

      // Store Key, remove ending ":"
      key = scan.next().replace(":","").trim();
      // Store Value
      value = scan.nextLine().trim();
      this.headers.put( key, value );
    }

    if( (this.headers.containsKey("Content-Length")) ){
      int size = new Integer(this.headers.get("Content-Length"));
      buff = new char[size];

      in.read(buff, 0, size);

      this.body = new String(buff);
      System.out.println(this.body);
    }
   /* while(true){
      line = scan.nextLine();

      if(line.equals("")){
        break;
      }

      Scanner scanLine = new Scanner(line);

      // Store Key, remove ending ":"
      key = scanLine.next().replace(":","").trim();
      // Store Value
      value = scanLine.nextLine().trim();
      this.headers.put( key, value );
    }*/
    //
    //  Parse and store body
    //
/*    while( (line = in.readLine()) != null ){
      if(line.equals("")){
        break;
      }

      this.body += line;
    }*/

  }

  public String getVerb(){
    return this.verb;
  }

  public String getUri(){
    return this.uri;
  }

  public String getHttpVersion(){
    return this.httpVersion;
  }

  public String getHeaders(){
    String headers = "";
    Set<String> keys = this.headers.keySet();

    for(String i : keys){
      headers += i + ": " + this.headers.get(i) + "\n";
    }
    headers += "\n";

    return headers;
  }

  public String getHeader(String key){
    return this.headers.get(key);
  }

  public String getIp(){
    return this.ipAddress;
  }

  public String getRequestLine(){
    return this.verb + " " + this.uri + " " + this.httpVersion + "\n";
  }

  public String getBody(){
    return this.body;
  }

  public void displayHeaders(){
    Set<String> keys = this.headers.keySet();

    for(String i : keys){
      System.out.print(i + " " + this.headers.get(i) + "\n");
    }
  }

  public String toString(){
    String request;

    request = this.getRequestLine();
    request += this.getHeaders();

    if(!this.body.isEmpty()){
      request += this.body;
    }
    return request;
  }

}


