package dbGateway;

import fsc.entity.session.AuthenticatedSession;
import fsc.gateway.SessionGateway;
import fsc.service.Authorizer;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

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

  private void saveSession() {
    gateway.addSession(session);
    gateway.commit();
  }

}