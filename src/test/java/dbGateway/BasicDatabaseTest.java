package dbGateway;

import fsc.app.AppContext;
import fsc.gateway.Gateway;
import org.junit.After;
import org.junit.Before;
import webserver.PersistentUnitLocator;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BasicDatabaseTest {
  protected final DatabaseBackedGatewayFactory gatewayFactory =
        new DatabaseBackedGatewayFactory(PersistentUnitLocator.get());
  protected DatabaseBackedGateway gateway;
  protected DatabaseBackedGateway anotherGateway;

  @Before
  public void setUp() {
    gateway = gatewayFactory.obtainGateway();
    anotherGateway = gatewayFactory.obtainGateway();
    gateway.begin();
    anotherGateway.begin();
  }

  @After
  public void tearDown() {
    gateway.close();
    anotherGateway.close();
  }

  protected <T> T returnWithNewContext(Function<AppContext, T> tasks) {
    return returnWithNewGateway(gateway -> tasks.apply(new AppContext(gateway)));

  }

  protected void withNewContext(Consumer<AppContext> tasks) {
    withNewGateway(gateway -> tasks.accept(new AppContext(gateway)));

  }

  protected <T> T returnWithNewGateway(Function<Gateway, T> gatewayTasks) {
    DatabaseBackedGateway gateway = gatewayFactory.obtainGateway();
    T returnedValue = gatewayTasks.apply(gateway);
    gateway.shutdown();
    return returnedValue;
  }

  protected void withNewGateway(Consumer<Gateway> gatewayTasks) {
    DatabaseBackedGateway gateway = gatewayFactory.obtainGateway();
    gatewayTasks.accept(gateway);
    gateway.shutdown();
  }
}
