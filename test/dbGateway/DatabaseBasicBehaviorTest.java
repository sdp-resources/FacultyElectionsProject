package dbGateway;

import fsc.entity.PasswordRecord;
import fsc.service.Authorizer;
import org.hibernate.TransactionException;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class DatabaseBasicBehaviorTest {
  private EntityManagerFactory managerFactory =
        Persistence.createEntityManagerFactory("inmemoryH2");

  @Test
  public void entityManagerStartsWithoutTransaction() {
    EntityManager manager = managerFactory.createEntityManager();
    assertFalse(manager.getTransaction().isActive());
  }

  @Test
  public void entityManagerDoesNotErrorIfPersistingWithoutTransaction() {
    EntityManager manager = managerFactory.createEntityManager();
    manager.persist(aPasswordRecord());
    assertFalse(manager.getTransaction().isActive());
  }

  @Test(expected = IllegalStateException.class)
  public void entityManagerErrorsIfCommittingWithoutTransaction() {
    EntityManager manager = managerFactory.createEntityManager();
    manager.persist(aPasswordRecord());
    manager.getTransaction().commit();
  }

  @Test
  public void transactionIsNoLongerActiveAfterACommit() {
    EntityManager manager = managerFactory.createEntityManager();
    manager.getTransaction().begin();
    manager.persist(aPasswordRecord());
    manager.getTransaction().commit();
    assertFalse(manager.getTransaction().isActive());
  }

  @Test
  public void entityManagerAcceptsFindRequestsWithoutTransaction() {
    EntityManager manager = managerFactory.createEntityManager();
    assertNull(manager.find(PasswordRecord.class, "admin"));
    assertFalse(manager.getTransaction().isActive());
  }

  @Test
  public void entityManagerAcceptsPersistedElementsBeforeTransactionStart() {
    EntityManager manager = managerFactory.createEntityManager();
    manager.persist(aPasswordRecord());
    manager.getTransaction().begin();
    manager.getTransaction().commit();
    assertNotNull(manager.find(PasswordRecord.class, "admin"));
    assertFalse(manager.getTransaction().isActive());
  }

  @Test(expected = TransactionException.class)
  public void entityManagerErrorsIfRollbackAfterCommitWithoutTransactionStart() {
    EntityManager manager = managerFactory.createEntityManager();
    manager.getTransaction().begin();
    manager.getTransaction().commit();
    manager.getTransaction().rollback();
  }

  @Test
  public void entityManagerDoesNotErrorIfRollbackRightAway() {
    EntityManager manager = managerFactory.createEntityManager();
    manager.getTransaction().rollback();
  }

  @Test
  public void entityManagerProvidesPersistedElementsWithoutRollback() {
    EntityManager manager = managerFactory.createEntityManager();
    manager.persist(aPasswordRecord());
    assertNotNull(manager.find(PasswordRecord.class, "admin"));
    assertFalse(manager.getTransaction().isActive());
  }

  @Test
  public void entityManagerDoesNotProvidePersistedElementsAfterARollback() {
    EntityManager manager = managerFactory.createEntityManager();
    manager.persist(aPasswordRecord());
    manager.getTransaction().begin();
    manager.getTransaction().rollback();
    assertNull(manager.find(PasswordRecord.class, "admin"));
    assertFalse(manager.getTransaction().isActive());
  }

  @Test
  public void entityManagerPreservesPersistedElementsThatWhereCommittedEvenIFARollbackFollowed() {
    EntityManager manager = managerFactory.createEntityManager();
    manager.persist(aPasswordRecord());
    manager.getTransaction().begin();
    manager.getTransaction().commit();
    manager.getTransaction().begin();
    manager.getTransaction().rollback();
    assertNotNull(manager.find(PasswordRecord.class, "admin"));
    assertFalse(manager.getTransaction().isActive());
  }


  private PasswordRecord aPasswordRecord() {
    return new PasswordRecord("admin", "something", Authorizer.Role.ROLE_ADMIN);
  }

}
