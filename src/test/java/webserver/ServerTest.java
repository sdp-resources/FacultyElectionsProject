package webserver;

import fsc.gateway.Gateway;
import org.junit.BeforeClass;
import spark.Spark;

import java.net.HttpURLConnection;
import java.net.URL;

public class ServerTest extends Server {
  protected static TestableRouter router;

  @BeforeClass
  public static void startSpark() {
    waitForSparkServerToStart();
  }

  private static void waitForSparkServerToStart() {
    if (!isRunning()) startServer();

    waitForServerToRun();
  }

  private static void startServer() {
    router = new TestableRouter();
    router.setupRoutes("");
    new DataFixture(router.getGateway()).populateDatabase();
  }

  private static boolean isRunning() {
    try {
      HttpURLConnection con = (HttpURLConnection)
                                    new URL("http://localhost:4568/login").openConnection();
      return con.getResponseCode() == 200;
    } catch (Exception e) {
      return false;
    }
  }

  private static void waitForServerToRun() {
    int tries = 10;
    while (tries > 0) {
      if (isRunning()) return;

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
      tries--;
    }
  }

  public static class TestableRouter extends Router {
    public Gateway getGateway() {
      return super.getGateway();
    }

    public void setupRoutes(String resourcePath) {
      Spark.port(4568);
      super.setPersistenceUnit(PersistentUnitLocator.get());
      super.setupRoutes(resourcePath);
    }

  }
}
