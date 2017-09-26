import java.util.*;

public class Test{

  public static void main(String[] args){
  /*  HashMap<String,String> hm = new HashMap<String,String>();

    hm.put("one","dog");
    hm.put("two","cat");
    hm.put("three","rat");

    Set<String> keys = hm.keySet();
    for(String i : keys){
      System.out.print(i);
      System.out.println(hm.get(i));
    }*/
    MimeTypes mime = new MimeTypes("/root/web-server-nguyen-nhan-alejo-norald/config/mime.types");
    mime.load();

    Set<String> keys = mime.types.keySet();
    for(String key : keys){
      System.out.println(key + " " + mime.lookup(key));
    }
  }
}
