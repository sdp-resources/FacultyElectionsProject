package fixtures;

import fsc.app.AppContext;
import gateway.InMemoryGateway;

public class TestContext {

  public static AppContext app;

  public TestContext() {
    app = new AppContext(new InMemoryGateway());
  }

}
