import java.util.*;
import java.net.*;
import java.io.*;

/**
*  Request Class handles incoming socket requests
*/

public class Request{
  private String uri;
  private String verb;
  private String httpVersion;
  private HashMap<String, String> headers = new HashMap<String, String>();

  public Request(Socket input){

    try{
      this.parse(input.getInputStream());
    } catch(Exception e){
      System.out.println("Error: " + e);
    }

  }

  /**
  *  Parses an inputStream expected to be from a Socket
  *
  */

  public void parse(InputStream input){

    /**
    *  Parse and store the verb, uri, and httpVersion of the initial request line
    *
    */
    Scanner scan = new Scanner(input);
    this.verb = scan.next();
    this.uri = scan.next();
    this.httpVersion = scan.next();
    scan.nextLine();

    /**
    *  Parse and store headers
    *  Headers come in keys and values
    *  Keys and values will all be lower cased
    */
    String line, key, value;
    while(true){
      line = scan.nextLine();
      if(line.equals("")){
        break;
      }
      Scanner scanLine = new Scanner(line);

      // Store Key, remove ending ":"
      key = scanLine.next().replace(":","").trim().toLowerCase();
      // Store Value
      value = scanLine.nextLine().trim().toLowerCase();
      headers.put( key, value );
    }
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

  public HashMap getHeaders(){
    return this.headers;
  }

  public String getHeader(String key){
    return this.headers.get(key);
  }

}


