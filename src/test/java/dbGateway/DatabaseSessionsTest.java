package dbGateway;

import fsc.MyTime;
import fsc.entity.session.AuthenticatedSession;
import org.junit.Test;

import static fsc.service.Authorizer.Role.ROLE_ADMIN;
import static org.junit.Assert.assertEquals;

public class DatabaseSessionsTest {

  private AuthenticatedSession session = makeSession("aToken", MyTime.fromNow(10));
  private RedisStore anotherGateway = new RedisStore();
  private RedisStore gateway = new RedisStore();

  @Test
  public void canAddSession() {
    saveSession();
    AuthenticatedSession session2 = anotherGateway.getSession(session.getToken());
    assertEquals(session, session2);
  }

  @Test
  public void outOfDateSessionsGetCleanedUpWhenMethodGetsCalled() {
    AuthenticatedSession currentSession = makeSession("valid", MyTime.fromNow(10));
    AuthenticatedSession expiredSession = makeSession("expired", MyTime.beforeNow(10));
    gateway.addSession(currentSession);
    gateway.addSession(expiredSession);

    assertEquals(null, gateway.getSession(expiredSession.getToken()));
    assertEquals(currentSession, gateway.getSession(currentSession.getToken()));
  }

  private AuthenticatedSession makeSession(String token, MyTime expires) {
    return new AuthenticatedSession(ROLE_ADMIN, "admin", token, expires);
  }

  private void saveSession() {
    gateway.addSession(session);
  }
}