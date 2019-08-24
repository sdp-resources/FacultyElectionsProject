package dbGateway;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseBackedGatewayFactory {
  private static Map<String, DatabaseBackedGatewayFactory> instances = new ConcurrentHashMap<>();
  private EntityManagerFactory sessionFactory;

  public DatabaseBackedGatewayFactory(String persistenceUnit) {
//    String persistenceUnit = "org.skiadas.local";
//    String persistenceUnit = "testdb";
    this.sessionFactory = Persistence.createEntityManagerFactory(persistenceUnit);
  }

  public static DatabaseBackedGatewayFactory getInstance(String persistenceUnit) {
    if (!instances.containsKey(persistenceUnit)) {
      instances.putIfAbsent(persistenceUnit,
                            new DatabaseBackedGatewayFactory(persistenceUnit));
    }
    return instances.get(persistenceUnit);
  }

  public DatabaseBackedGateway obtainGateway() {
    return new DatabaseBackedGateway(sessionFactory.createEntityManager());
  }
}