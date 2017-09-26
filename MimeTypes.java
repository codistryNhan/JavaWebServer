import java.io.*;
import java.util.*;

public class MimeTypes{
  private HashMap<String, String> mimes = new HashMap<String, String>();

  public MimeTypes(String filename){

    try {

      File file = new File("/root/web-server-nguyen-nhan-alejo-norald/config/mime.types");
      Scanner scan = new Scanner(file);

      while(scan.hasNextLine()){

        String line = scan.nextLine();

        if( !(line.isEmpty() || line.startsWith("#")) ){

          Scanner innerScan = new Scanner(line);

          String mimeType = innerScan.next();
          String extension = "";

          while(innerScan.hasNext()){
            extension = innerScan.next();
            mimes.put(extension, mimeType);
          }

        }

      }

    } catch (Exception e){
      System.out.println("File Error at MimeTypes" + e);
    }


  }

  public String get(String key){
    return this.mimes.get(key); 
  }

}
