import java.io.*;
import java.net.*;
import java.util.*;
import java.time.LocalDateTime;

public class Response{
  public int code;
  public String reasonPhrase;
  public Resource resource;
  public String absolutePath;

  private String serverinfo = System.getProperty("os.name") + " " + System.getProperty("os.version");
  private String date = LocalDateTime.now().toString();
  private String httpVer = "HTTP/1.1";

  public Response(Resource resource){
    this.absolutePath = resource.getAbsolutePath();
  }

  public void send(OutputStream socketOut, int code) throws IOException{

    String response = this.createResponseHeader(code);
    PrintWriter out = new PrintWriter(socketOut, true);

    //
    //  For codes that requiest extra headers/body
    //
    switch(code){
      case 200:  int fileSize = (int)new File(this.absolutePath).length();
                 char[] fileContent = new char[fileSize];

                 FileReader in = new FileReader(this.absolutePath);
                 in.read(fileContent);

                 response += this.createHeader("Content-Type", "text/html");
                 response += this.createHeader("Content-Length", String.valueOf(fileSize));
                 response += "\n";
                 out.print(response);
                 out.println(fileContent);
                 break;
      default:   out.println(response);
                 break;
    }

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

/*  private boolean fileExist(){
    try {
      File file = new File(url)
    }
  }*/
}
