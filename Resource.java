import java.io.*;

public class Resource{

  private String absolutePath;
  private String dirIndex;
  private String docRoot;
  private MimeTypes mime = new MimeTypes("/root/web-server-nguyen-nhan-alejo-norald/config/mime.types");

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
  private String appendDirIndex(String url){
    if (!url.endsWith("/")) {
      url = url + "/";
    }

    return url += this.dirIndex;
  }

  //
  //  Remove trailing '/' from url and concats DocRoot
  //
  private String appendDocRoot(String url){
    String root = this.docRoot;

    if(root.endsWith("/")){
      root = root.substring(0, root.length() - 1);
    }

    return url = root + url;
  }

  //
  //  Check if file exists on the server
  //
  public boolean fileExist(){
    return new File(this.absolutePath).isFile();
  }

  //
  //  Creates the resource on the server
  //
  public void createResource(String body) throws IOException{
    File file = new File(this.absolutePath);

    if(file.exists()){
      file.delete();
    }

    file.createNewFile();

    FileWriter fileOut = new FileWriter(file, true);
    fileOut.write(body);
    fileOut.close();
  }

  //
  //  Deletes the resource on the server
  //
  public void deleteResource() throws IOException{
    File file = new File(this.absolutePath);

    file.delete();
  }

  //
  //  Get Extension of file
  //
  private String getFileExtension(){
    String extension = "";
    String filename = this.absolutePath;

    int position = filename.lastIndexOf('.');
    if(position > 0){
      extension = filename.substring(position+1);
    }

    return extension;
  }

  //
  //  Get MimeType
  //
  public String getMimeType(){
    String key = this.getFileExtension();

    return mime.get(key);
  }

  //
  //
  //

}

