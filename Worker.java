import java.io.*;
import java.util.*;
import java.net.*;

public class Worker implements Runnable{


  private HttpdConf config;
  private MimeTypes mime;
  private int PORT;
  Socket client;
  OutputStream out = null;

  private Thread worker;

  public Worker(Socket client){

    try {
      this.config = new HttpdConf("/root/web-server-nguyen-nhan-alejo-norald/config/httpd.conf");
      this.mime = new MimeTypes("/root/web-server-nguyen-nhan-alejo-norald/config/mime.types");
      this.PORT = new Integer(config.get("Listen"));
      this.client = client;
      this.out = client.getOutputStream();
    } catch(Exception e){
      System.out.println(e);
    }

    if(worker == null){
      worker = new Thread(this);

      worker.start(); 
   }

  }

  public void run(){

      try {

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
                         response.send(out,2001);
                         System.out.println(request.getBody());
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

      } catch(Exception e){
        System.out.println(e);
      }

  }

}

