package fixtures;

import dbGateway.DatabaseBackedGatewayFactory;
import webserver.PersistentUnitLocator;

public class TestContext {
  public static AppContextWrapper app;

  public TestContext() {
    DatabaseBackedGatewayFactory gatewayFactory =
          new DatabaseBackedGatewayFactory(PersistentUnitLocator.get());
    app = new AppContextWrapper(gatewayFactory.obtainGateway());
  }
}
