import java.net.*;
import java.io.*;
import java.util.*;

public class SimpleServer{

  public static final int PORT = 3000;

  public static void main(String[] args){

    try {

    ServerSocket socket = new ServerSocket(PORT);

    Socket client = null;

    client = socket.accept();

    HttpdConf config = new HttpdConf("/root/server/config/httpd.conf");

    Request req = new Request(client);

    Resource res = new Resource(req.getUri(), config);

    System.out.println(config.aliasKeyExist("/ab/"));
    System.out.println(req.getUri());
    System.out.println(res.getAbsolutePath());

    Response response = new Response(res);
    response.send(client);

   // PrintWriter out = new PrintWriter(client.getOutputStream(), true);
   //out.println("Hello");

    //client.close();
    } catch(Exception e){
      System.out.println("Error ");
      e.printStackTrace(System.out);
    }
    
  }

  

}
