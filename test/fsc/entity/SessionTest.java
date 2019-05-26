package fsc.entity;

import fsc.entity.session.AuthorizedSession;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotSame;

public class SessionTest {
  private AuthorizedSession session;

  @Test
  public void javaDatesWeird() throws InterruptedException {
    assertNotSame(Calendar.getInstance(), Calendar.getInstance());
    assertNotSame(new GregorianCalendar(), new GregorianCalendar());

    Calendar calendar = Calendar.getInstance();
    Date initalTime = calendar.getTime();
    Thread.sleep(100);
    assertEquals(initalTime, calendar.getTime());
  }

  @Test
  public void canCreateSession()
  {
    String role = "Administrator";
    String username = "admin";
    String token = "random-string";

    Calendar expirationTime = Calendar.getInstance();
    expirationTime.add(Calendar.MINUTE, 10);

    session = new AuthorizedSession(role, username, token, expirationTime);

    assertEquals(token, session.getToken());
    assertEquals(username, session.getUsername());
    assertEquals(role, session.getRole());
    assertEquals(expirationTime, session.getExpirationTime());
  }
}
