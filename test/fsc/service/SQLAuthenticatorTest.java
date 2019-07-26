package fsc.service;

import fsc.entity.PasswordRecord;
import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SQLAuthenticatorTest {

  private static final String ADMIN = "admin";
  private static final String PASSWORD = "password";
  private static final String WRONG_PASSWORD = "wrong";
  private Credentials credentials;
  private Authenticator authenticator;
  private Session session;
  private SimplePasswordGatewaySpy gateway;
  private String hashedPassword;

  @Before
  public void setUp() {
    credentials = new Credentials(ADMIN, PASSWORD);
    gateway = new SimplePasswordGatewaySpy();
    authenticator = new SQLAuthenticator(gateway);
    hashedPassword = ((SQLAuthenticator) authenticator).hashPassword(credentials);
  }

  @Test
  public void ifNameNotInDatabase_returnUnauthenticatedSession() {
    session = authenticator.authenticateWithCredentials(credentials);
    assertFalse(session.isAuthenticated());
    assertEquals(gateway.requestedUsername, credentials.getUsername());
  }

  @Test
  public void ifNameIsInDatabaseButPasswordIsWrong_returnUnauthenticatedSession() {
    PasswordRecord record = new PasswordRecord(ADMIN, PASSWORD, Authorizer.Role.ROLE_ADMIN);
    gateway.addPasswordRecord(record);
    Credentials falseCredentials = new Credentials(ADMIN, WRONG_PASSWORD);
    session = authenticator.authenticateWithCredentials(falseCredentials);
    assertFalse(session.isAuthenticated());
    assertEquals(gateway.requestedUsername, credentials.getUsername());
  }

  @Test
  public void ifNameIsInDatabaseAndPasswordMatches_returnAuthenticatedSession() {
    PasswordRecord record = new PasswordRecord(ADMIN, hashedPassword, Authorizer.Role.ROLE_ADMIN);
    gateway.addPasswordRecord(record);
    session = authenticator.authenticateWithCredentials(credentials);
    assertTrue(session.isAuthenticated());
    AuthenticatedSession authenticatedSession = (AuthenticatedSession) this.session;
    assertNotNull(authenticatedSession.getExpirationTime());
    assertEquals(ADMIN, authenticatedSession.getUsername());
    assertEquals(record.getRole(), authenticatedSession.getRole());
    assertEquals(gateway.requestedUsername, credentials.getUsername());
  }
}