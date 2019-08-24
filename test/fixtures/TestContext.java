package fixtures;

import dbGateway.DatabaseBackedGatewayFactory;

public class TestContext {
  public static AppContextWrapper app;

  public TestContext() {
    // TODO: Make this part more configurable
    //
    //    app = new AppContextWrapper(new InMemoryGateway());
    DatabaseBackedGatewayFactory gatewayFactory = new DatabaseBackedGatewayFactory("inmemoryH2");
    app = new AppContextWrapper(gatewayFactory.obtainGateway());
  }
}
