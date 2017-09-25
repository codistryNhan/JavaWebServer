import java.io.*;
import java.util.*;
import java.time.*;

public class Logger{

  private String filename;

  public Logger(String filename){
    this.filename = filename;
  }

  //
  //  Writes to log file
  //    Common Log Format
  //    remotehost username auth-username timestamp request-line response-code response-size
  //
  public void write(Request req, Response res) throws IOException{
    String hostname = req.getIp();
    String date = LocalDateTime.now().toString();
    String reqLine = req.getRequestLine();
    int resCode = res.getResponseCode();

    String logInput = hostname + " " + date + " " + reqLine + " " + resCode + "\n";

    File file = new File(this.filename);

    if(!file.exists()){
      file.createNewFile();

    }

    FileWriter fileOut = new FileWriter(file, true);
    fileOut.write(logInput);
    fileOut.close();
  }
}

