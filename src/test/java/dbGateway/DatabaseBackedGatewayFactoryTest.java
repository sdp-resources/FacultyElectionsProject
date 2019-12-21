package dbGateway;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.function.Supplier;

public class DatabaseBackedGatewayFactoryTest {

  private DatabaseBackedGatewayFactory factory;

  @Ignore
  @Test
  public void factoryRecoversFromDisconnectedDatabase() {
    factory = new DatabaseBackedGatewayFactory("testInDocker");
    waitForTrue(true, () -> factory.isReady());
  }

  private void waitForTrue(Object expected, Supplier<Boolean> test) {
    int count = 1;
    while (count < 5) {
      if (test.get().equals(expected)) { return; }
      try {
        Thread.sleep(1000);
        count += 1;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      }
    }
    Assert.fail("Timed out");
  }
}