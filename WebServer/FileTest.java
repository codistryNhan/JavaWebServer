import java.io.*;

public class FileTest{

  public static void main(String[] args){
    File file = new File("/url/file.txt");

    try {
      file.getCanonicalFile();
      System.out.println(true);
    } catch (IOException e){
      System.out.println(false);
    }
  }

}
