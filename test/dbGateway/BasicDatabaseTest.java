package dbGateway;

import org.junit.After;
import org.junit.Before;

public class BasicDatabaseTest {
  protected final DatabaseBackedGatewayFactory gatewayFactory = new DatabaseBackedGatewayFactory();
  protected DatabaseBackedGateway gateway;
  protected DatabaseBackedGateway anotherGateway;

  @Before
  public void setUp() {
    gateway = gatewayFactory.beginSession();
    anotherGateway = gatewayFactory.beginSession();

  }

  @After
  public void tearDown() {
    gateway.close();
    anotherGateway.close();
  }
}
