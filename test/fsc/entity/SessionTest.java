package fsc.entity;

import fsc.entity.session.AuthenticatedSession;
import fsc.service.Authorizer;
import org.junit.Test;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;
import static junit.framework.TestCase.assertEquals;

public class SessionTest {
  private AuthenticatedSession session;

  @Test
  public void canCreateSession() {
    Authorizer.Role role = Authorizer.Role.ROLE_ADMIN;
    String username = "admin";
    String token = "random-string";

    LocalDateTime expirationTime = LocalDateTime.now().plus(10, MINUTES);

    session = new AuthenticatedSession(role, username, token, expirationTime);

    assertEquals(token, session.getToken());
    assertEquals(username, session.getUsername());
    assertEquals(role, session.getRole());
    assertEquals(expirationTime, session.getExpirationTime());
  }
}
