package dbGateway;

import fsc.entity.session.AuthenticatedSession;
import fsc.gateway.SessionGateway;
import fsc.service.Authorizer;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DatabaseSessionsTest extends BasicDatabaseTest {

  private AuthenticatedSession session = new AuthenticatedSession(
        Authorizer.Role.ROLE_ADMIN,
        "admin",
        "aToken",
        LocalDateTime.now());

  @Test
  public void canAddSession() throws SessionGateway.InvalidOrExpiredTokenException {
    saveSession();
    AuthenticatedSession session2 = anotherGateway.getSession(session.getToken());
    assertEquals(session, session2);
  }

  @Test
  public void outOfDateSessionsGetCleanedUpWhenMethodGetsCalled() {
    AuthenticatedSession currentSession = makeSession("valid",
                                                      LocalDateTime.now().plusMinutes(10));
    AuthenticatedSession expiredSession = makeSession("expired",
                                                      LocalDateTime.now().minusMinutes(10));
    gateway.addSession(currentSession);
    gateway.addSession(expiredSession);
    gateway.commit();
    gateway.cleanUpSessions();

    List<AuthenticatedSession> sessions = anotherGateway.getAllSessions();
    assertEquals(1, sessions.size());
    for (AuthenticatedSession session : sessions) {
      assertTrue(session.hasExpired());
    }
  }

  private AuthenticatedSession makeSession(String token, LocalDateTime expires) {
    return new AuthenticatedSession(Authorizer.Role.ROLE_ADMIN, "admin", token, expires);
  }

  private void saveSession() {
    gateway.addSession(session);
    gateway.commit();
  }

}