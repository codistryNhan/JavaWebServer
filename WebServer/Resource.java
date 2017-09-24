import java.io.*;

public class Resource{

  private String absolutePath;

  public Resource(String uri, HttpdConf config){
    String url = uri;

    //
    //  If URI is Aliased
    //

    if(config.aliasKeyExist(url)){
      url = config.getAliasValue(url);

      if(this.isFile(url)){
        this.absolutePath = url;
      } else {
        this.absolutePath = url;
      }

    //
    //  If URI is ScriptAliased
    //

    } else if(config.scriptAliasKeyExist(url)){
      url = config.getScriptAliasValue(url);

      if(this.isFile(url)){
        this.absolutePath = url;
      } else {
        this.absolutePath = url;
      }

    //
    //  URI is unmodified
    //

    } else {

      String docRoot = config.getDocRoot();
      //  Remove quotes from docRoot
      //  Add DocRoot to Url
      //  Add back quotes
      docRoot = docRoot.replaceAll("^\"|\"$", "");
      url = docRoot.concat(url);
      //url = "\"" + url + "\"";

      if(this.isFile(url)){
        this.absolutePath = url;
      } else {
        this.absolutePath = url;
      }
    }

  }

  public String getAbsolutePath(){
    return this.absolutePath;
  }

  //public boolean isScript(){}

  //public boolean isProtected(){}

  /**
  *  Checks if the string is a valid filename
  */
  public boolean isFile(String url){
    try{

      File file = new File(url);

      file.getCanonicalFile();
      return true;
    } catch (Exception e){
      return false;
    }

  }
}

