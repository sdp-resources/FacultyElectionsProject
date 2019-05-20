package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SessionTest {
  Session session;

  @Before
  public void setup() {
    session = new Session("Administrator","admin", "random-string");
  }

  @Test
  public void canGetSessionToken() {
    assertEquals("random-string",session.getToken());
  }

  @Test
  public void canGetSessionUsername() {
    assertEquals("admin",session.getUsername());
  }

  @Test
  public void canGetSessionRole() {
    assertEquals("Administrator",session.getRole());
  }
}
