import java.io.*;

public class Resource{

  private String absolutePath;
  private String dirIndex;
  private String docRoot;

  public Resource(String uri, HttpdConf config){
    //  Remove Quotes on URL
    String url = uri;
    this.dirIndex = config.get("DirIndex");
    this.docRoot = config.get("DocumentRoot");

    //
    //  If URI is Aliased
    //

    if(config.aliasKeyExist(url)){
      url = config.getAliasValue(url);

      if(this.isFile(url)){
        this.absolutePath = url;
      } else {
        this.absolutePath = this.appendDirIndex(url);
      }

    //
    //  If URI is ScriptAliased
    //

    } else if(config.scriptAliasKeyExist(url)){
      url = config.getScriptAliasValue(url);

      if(this.isFile(url)){
        this.absolutePath = url;
      } else {
        this.absolutePath = this.appendDirIndex(url);
      }

    //
    //  URI is unmodified
    //

    } else {

      url = this.appendDocRoot(url);

      if(this.isFile(url)){
        this.absolutePath = url;
      } else {
        this.absolutePath = this.appendDirIndex(url);
      }
    }

  }

  public String getAbsolutePath(){
    return this.absolutePath;
  }

  //public boolean isScript(){}

  //public boolean isProtected(){}

  //
  //  Checks if the string is a valid filename
  //
  public boolean isFile(String url){
    return url.contains(".");
  }

  //
  //  Adds '/' to url and appends DirIndex
  //
  public String appendDirIndex(String url){
    if (!url.endsWith("/")) {
      url = url + "/";
    }

    return url += this.dirIndex;
  }

  //
  //  Remove trailing '/' from url and concats DocRoot
  //
  public String appendDocRoot(String url){
    String root = this.docRoot;

    if(root.endsWith("/")){
      root = root.substring(0, root.length() - 1);
    }

    return url = root + url;
  }
}

