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
  private HashMap<String, String> headers = new HashMap<String, String>();

  public Request(Socket input){

    try{
      this.parse(input.getInputStream());
    } catch(Exception e){
      System.out.println("Error: " + e);
    }

  }

  //
  //  Parse and store request message's metadata
  //

  public void parse(InputStream input){

    //
    //  Parse and store the verb, uri, and httpVersion of the initial request line
    //

    Scanner scan = new Scanner(input);
    this.verb = scan.next();
    this.uri = scan.next();
    this.httpVersion = scan.next();
    scan.nextLine();

    //
    //  Parse and store headers
    //

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

    //
    //  Parse and store body
    //
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


