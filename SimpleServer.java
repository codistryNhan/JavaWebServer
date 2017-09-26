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

      Request request = new Request(client);
      Resource resource = new Resource(request.getUri(), config);
      Response response = new Response(resource);
      System.out.println(request);

      switch(request.getVerb()){
        case "GET": if(resource.fileExist()){
                      response.send(out, 200);
                    } else {
                      response.send(out,404);
                    }
                    break;

        case "HEAD": if(resource.fileExist()){
                       response.send(out, 2001);
                     } else {
                       response.send(out,404);
                     }
                     break;

        case "PUT": resource.createResource(request.getBody());
                    response.send(out, 201);
                    break;

        case "POST": if(resource.fileExist()){
                       response.send(out,200);
                     } else {
                       response.send(out,404);
                     }
                     break;
        case "DELETE": if(resource.fileExist()){
                         resource.deleteResource();
                         response.send(out, 204);
                       } else {
                         response.send(out, 404);
                       }
                       break;

        default: response.send(out, 400);
                   break;
      }

      //  Log transaction
      Logger log = new Logger(config.get("LogFile"));
      log.write(request,response);

    }
    //client.close();
    } catch(Exception e){
      System.out.println("Error ");
      e.printStackTrace(System.out);
    }
    
  }

  

}
