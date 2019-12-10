package webserver;

import java.io.InputStream;

public class AssetLoader {
  private static String basePath = "/assets";

  public AssetLoader() { }

  public String pathTo(String resource) {
    return basePath + resource;
  }

  public InputStream resourceAt(String resource) {
    return this.getClass().getResourceAsStream(pathTo(resource));
  }
}