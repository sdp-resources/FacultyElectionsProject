package dbGateway;

import fsc.app.AppContext;
import fsc.entity.EntityFactory;
import fsc.entity.Profile;

import javax.persistence.EntityManager;

public class DatabaseBackedGateway {
  private final EntityManager entityManager;
  private EntityFactory entityFactory;

  public DatabaseBackedGateway(EntityManager entityManager) {
    this.entityManager = entityManager;
    entityFactory = new PersistingEntityFactory(AppContext.getEntityFactory(), entityManager);
    begin();
  }

  private void begin() {
    entityManager.getTransaction().begin();
  }

  public void commit() {
    entityManager.getTransaction().commit();
  }

  public <T> T find(Class<T> aClass, Object o) {
    return entityManager.find(aClass, o);
  }

  public void close() {
    entityManager.close();
  }

  public void commitAndClose() {
    commit();
    close();
  }

  public void refresh(Object o) {
    entityManager.refresh(o);
  }

  public Profile createProfile(
        String name, String username, String division, String contract
  ) {
    return entityFactory.createProfile(name, username, division, contract);
  }

  public Profile findProfile(String username) {
    return find(Profile.class, username);
  }
}
