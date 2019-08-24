package webserver;

import spark.servlet.SparkApplication;

public class Server implements SparkApplication {

  // By default serving at http://localhost:4567
  public static void main(String[] args) {
    Router router = new Router();
    router.setupRoutes("");
    new DataFixture(router.gateway).populateDatabase();
  }

  // Used by filter within Tomcat
  public void init() {
    new Router().setupRoutes("");
  }
}
