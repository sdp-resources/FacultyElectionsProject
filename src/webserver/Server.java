package webserver;

import spark.servlet.SparkApplication;

public class Server implements SparkApplication {

  // By default serving at http://localhost:4567
  public static void main(String[] args) {
    Router.setupRoutes("");
  }

  // Used by filter within Tomcat
  public void init() {
    Router.setupRoutes("");
  }
}
