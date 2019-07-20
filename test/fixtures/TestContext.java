package fixtures;

import dbGateway.DatabaseBackedGatewayFactory;
import fsc.app.AppContext;

public class TestContext {
  public static AppContext app;

  public TestContext() {
    // TODO: Make this part more configurable
    //
    //    app = new AppContext(new InMemoryGateway());
    DatabaseBackedGatewayFactory gatewayFactory = new DatabaseBackedGatewayFactory();
    app = new AppContext(gatewayFactory.obtainGateway());
  }

  public static void closeAppContext() {
    app.shutdown();
  }
}
