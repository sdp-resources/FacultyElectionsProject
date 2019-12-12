package webserver;

import spark.Spark;
import spark.servlet.SparkApplication;

public class Server implements SparkApplication {

  // By default serving at http://localhost:4567
  public static void main(String[] args) {
    Router router = new Router();
    router.setPersistenceUnit(PersistentUnitLocator.get());
    Spark.port(readServerPort());
    router.setupRoutes("");

    new DataFixture(router.gateway).populateDatabase();
  }

  private static int readServerPort() {
    String port1 = System.getenv("FEC_SERVER_PORT");
    if (port1 == null) return 4567;
    return Integer.parseInt(port1);
  }

  // Used by filter within Tomcat
  public void init() {
    new Router().setupRoutes("");
  }
}
