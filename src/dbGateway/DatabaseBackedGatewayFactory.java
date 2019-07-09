package dbGateway;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseBackedGatewayFactory {
  private EntityManagerFactory sessionFactory;

  public DatabaseBackedGatewayFactory() {
    this.sessionFactory = Persistence.createEntityManagerFactory("org.skiadas.local");
  }

  public DatabaseBackedGateway beginSession() {
    return new DatabaseBackedGateway(sessionFactory.createEntityManager());
  }
}