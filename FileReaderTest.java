import java.io.*;

public class FileReader{

  public static void main(String[] args){

  try( FileInputStream in = new FileInputStream("index.html") ){
    boolean eof = false;
    int count = 0;

    FileOutputStream out = new FileOutputStream("result.html");

    while(!eof){
      int input = in.read();

      if(input == -1){
        eof = true;
        break;
      } else {
        count++;
      }

      out.write(input);
    }

    in.close();
    out.close();
    System.out.println("\nBytes read: " + count);

  } catch (IOException e){
    System.out.println("Error --" + e.toString());
  }


  }
}
