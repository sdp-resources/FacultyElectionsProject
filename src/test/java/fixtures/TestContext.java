package fixtures;

import dbGateway.DatabaseBackedGatewayFactory;
import webserver.PersistentUnitLocator;

public class TestContext {
  public static AppContextWrapper app;

  public TestContext() {
    // TODO: Make this part more configurable
    //
    //    app = new AppContextWrapper(new InMemoryGateway());
    DatabaseBackedGatewayFactory gatewayFactory =
          new DatabaseBackedGatewayFactory(PersistentUnitLocator.get());
    app = new AppContextWrapper(gatewayFactory.obtainGateway());
  }
}
