import java.io.*;
import java.net.*;
import java.util.*;

public class Response{
  public int code;
  public String reasonPhrase;
  public Resource resource;
  public String response;
  public String absolutePath;

  public Response(Resource resource){
    this.absolutePath = resource.getAbsolutePath();
  }

  public void send(Socket socket) throws IOException{
    int fileSize = (int)new File(this.absolutePath).length();
    char[] fileContent = new char[fileSize];

    FileReader in = new FileReader(this.absolutePath);
    in.read(fileContent);

    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    this.response = "HTTP/1.1 200 OK\n";
    this.response += "Content-type: text/plain\n";
    this.response += "Content-length: " + fileSize + "\n";
    this.response += "\n";
    out.print(this.response);
    out.println(fileContent);
  }

/*  private boolean fileExist(){
    try {
      File file = new File(url)
    }
  }*/
}
