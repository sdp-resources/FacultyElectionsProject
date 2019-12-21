package dbGateway;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class DatabaseBackedGatewayFactory {
  private static Map<String, DatabaseBackedGatewayFactory> instances = new ConcurrentHashMap<>();
  private final AtomicReference<EntityManagerFactory> sessionFactory = new AtomicReference<>();
  private String persistenceUnit;

  public DatabaseBackedGatewayFactory(String persistenceUnit) {
    this.persistenceUnit = persistenceUnit;
    acquireEntityFactoryAsync();
  }

  private void acquireEntityFactoryAsync() {
    acquireEntityManagerFactory();
    //    new Thread(this::acquireEntityManagerFactory).start();
  }

  private void acquireEntityManagerFactory() {
    while (!isReady()) {
      try {
        tryToAcquireFactory();
        return;
      } catch (PersistenceException e) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
          Thread.currentThread().interrupt();
          throw new RuntimeException(ex);
        }
      }
    }
  }

  private void tryToAcquireFactory() {
    Map<String, String> hash = new HashMap<>();
    System.setProperty("db.host", getEnvDefault("DB_HOST", "fec_db"));
    System.setProperty("db.name", getEnvDefault("DB_NAME", "testdb"));
    this.sessionFactory.set(Persistence.createEntityManagerFactory(persistenceUnit, hash));
  }

  private String getEnvDefault(String envVarName, String defaultValue) {
    String value = System.getenv(envVarName);
    return value == null ? defaultValue : value;
  }

  public static DatabaseBackedGatewayFactory getInstance(String persistenceUnit) {
    if (!instances.containsKey(persistenceUnit)) {
      instances.putIfAbsent(persistenceUnit,
                            new DatabaseBackedGatewayFactory(persistenceUnit));
    }
    return instances.get(persistenceUnit);
  }

  public DatabaseBackedGateway obtainGateway() {
    if (!isReady()) {
      acquireEntityFactoryAsync();
      throw new RuntimeException("Gateway unavailable.");
    }
    return new DatabaseBackedGateway(sessionFactory.get().createEntityManager());
  }

  public boolean isReady() {
    return sessionFactory.get() != null;
  }
}