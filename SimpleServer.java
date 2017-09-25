import java.net.*;
import java.io.*;
import java.util.*;

public class SimpleServer{

  public static void main(String[] args){

    try {

    HttpdConf config = new HttpdConf("/root/web-server-nguyen-nhan-alejo-norald/config/httpd.conf");
    MimeTypes mime = new MimeTypes("/root/web-server-nguyen-nhan-alejo-norald/config/mime.types");

    int PORT = new Integer(config.get("Listen"));

    ServerSocket socket = new ServerSocket(PORT);

    Socket client = null;
    OutputStream out = null;
    while(true){

      client = socket.accept();
      out = client.getOutputStream();

      Request req = new Request(client);
      Resource res = new Resource(req.getUri(), config);
      Response response = new Response(res);

      if(req.getVerb().equals("GET")){
        if(!response.fileExist()){
          response.send(out, 404);
        } else {
          response.send(out,200);
        }
      }


      Logger log = new Logger(config.get("LogFile"));
      log.write(req,response);

    }
   // PrintWriter out = new PrintWriter(client.getOutputStream(), true);
   //out.println("Hello");

    //client.close();
    } catch(Exception e){
      System.out.println("Error ");
      e.printStackTrace(System.out);
    }
    
  }

  

}
