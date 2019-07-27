package dbGateway;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseBackedGatewayFactory {
  private EntityManagerFactory sessionFactory;

  public DatabaseBackedGatewayFactory() {
//    String persistenceUnitName = "org.skiadas.local";
//    String persistenceUnitName = "testdb";
    String persistenceUnitName = "inmemoryH2";
    this.sessionFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
  }

  public DatabaseBackedGateway obtainGateway() {
    return new DatabaseBackedGateway(sessionFactory.createEntityManager());
  }
}