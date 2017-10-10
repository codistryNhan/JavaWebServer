import java.io.*;
import java.util.*;
import java.net.*;

//
//  Entry Point for WebServer
//  Run this class and send HTTP Requests to localhost
//

public class WebServer{

  public static void main(String[] args){
    try {
    int port = 3000;
    ServerSocket socket = new ServerSocket(port);

    while(true){
      Socket client = socket.accept();
      Worker worker = new Worker(client);
    }

    } catch(Exception e){
      System.out.println(e);
    }
  }

}
