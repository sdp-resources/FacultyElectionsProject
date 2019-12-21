package dbGateway;

import fsc.entity.session.AuthenticatedSession;
import fsc.service.Authorizer;
import fsc.service.SessionCreator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RedisStoreTest {

  private RedisStore store;
  private SessionCreator creator;

  @Before
  public void setUp() {
    store = new RedisStore();
    creator = new SessionCreator();
  }

  @Test
  public void canAddValue() {
    store.set("key", "value");
    assertEquals("value", store.get("key"));
  }

  @Test
  public void canStoreSession() {
    AuthenticatedSession session = creator.createSession(Authorizer.Role.ROLE_ADMIN, "admin");
    store.addSession(session);
    AuthenticatedSession returned = store.getSession(session.getToken());
    assertEquals(session, returned);
  }

  @Test
  public void incorrectTokenReturnsNull() {
    assertEquals(null, store.get("something"));
  }
}