import java.io.*;
import java.nio.file.Files;
import java.net.*;
import java.util.*;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Response{
  public int code;
  public String reasonPhrase;
  public Resource resource;
  public String absolutePath;

  private String serverinfo = System.getProperty("os.name") + " " + System.getProperty("os.version");
  private String date = LocalDateTime.now().toString();
  private String httpVer = "HTTP/1.1";

  public Response(){

  }

  public Response(Resource resource){
    this.resource = resource;
    this.absolutePath = resource.getAbsolutePath();
  }

  public void send(OutputStream socketOut, int code) throws IOException{

    String response = this.createResponseHeader(code);
    PrintWriter out = new PrintWriter(socketOut, true);
    int fileSize;
    //BufferedOutputStream byteOut = new BufferedOutputStream(socketOut);

    //
    //  For codes that requiest extra headers/body
    //  2001 for HEAD and POST Success
    switch(code){
     case 2001:  fileSize = (int)new File(this.absolutePath).length();

                 response += this.createHeader("Content-Type", resource.getMimeType());
                 response += this.createHeader("Content-Length", String.valueOf(fileSize));
                 response += "\n";
                 out.println(response);
                 break;

      case 200:  fileSize = fileSize = (int)new File(this.absolutePath).length();
                 response += this.createHeader("Content-Type", resource.getMimeType());
                 response += this.createHeader("Content-Length", String.valueOf(fileSize));
                 response += "\n";
                 out.print(response);

                 if(resource.isImage()){
                   File image = new File(this.absolutePath);
                   //byte[] imageFile = Files.readAllBytes(image.toPath());
                   /*BufferedImage image = ImageIO.read(new File(this.absolutePath));

                   ByteArrayOutputStream fileContent = new ByteArrayOutputStream();
                   ImageIO.write(image, resource.getFileExtension(), socketOut);*/
                   //String imageOut = "<img src=\"data:" + resource.getMimeType() + ";base64,\"" + imageFile +  " />";
                   //out.write(imageOut);
                   //out.flush();
                   //out.close();
                   Files.copy(image.toPath(), socketOut);
                   //socketOut.close();

                 } else {
                   char[] fileContent = new char[fileSize];

                   //FileInputStream in = new FileInputStream(this.absolutePath);
                   FileReader in = new FileReader(this.absolutePath);
                   in.read(fileContent);

                   out.write(fileContent);
                   out.close();
                 }

                 break;

      case 201:  response += this.createHeader("Content-Type", resource.getMimeType());
                 response += "\n";
                 response += this.absolutePath;
                 out.println(response);
                 break;

      case 401:  response += this.createHeader("WWW-Authenticate", "Basic realm=\"realm\"");
                 out.println(response);
                 break;

      default:   out.println(response);
                 break;
    }
    out.close();
  }

  public int getResponseCode(){
    return this.code;
  }

  //
  //  Create Response Line
  //
  private String createResponseLine(int code, String phrase){
    String responseLine = this.httpVer + " " + code + " " + phrase + "\n";
    return responseLine;
  }

  //
  //  Create Headers
  //
  private String createHeader(String key, String value){
    key = key.trim();
    value = value.trim();

    String header = key + ": " + value + "\n";
    return header;
  }

  //
  //  Create appropriate headers by code status
  //
  private String createResponseHeader(int code){
    //200, 201, 204, 400, 401, 403, 404, 500
    this.code = code;
    String responseLine;
    String responseHeaders;

    switch(code){
     case 2001: responseLine = this.createResponseLine(200, "OK");
                break;
      case 200: responseLine = this.createResponseLine(200,"OK");
                break;
      case 201: responseLine = this.createResponseLine(201, "Created");
                break;
      case 204: responseLine = this.createResponseLine(204, "No Content");
                break;
      case 400: responseLine = this.createResponseLine(400, "Bad Request");
                break;
      case 401: responseLine = this.createResponseLine(401, "Unauthorized");
                break;
      case 403: responseLine = this.createResponseLine(403, "Forbidden");
                break;
      case 404: responseLine = this.createResponseLine(404, "Not Found");
                break;
      case 500: responseLine = this.createResponseLine(500, "Internal Server Error");
                break;
      default:  responseLine = this.createResponseLine(500, "Internal Server Error");
                break;
    }

    responseHeaders = this.createHeader("Server", this.serverinfo);
    responseHeaders += this.createHeader("Date", this.date);

    return responseLine + responseHeaders;
  }

}
