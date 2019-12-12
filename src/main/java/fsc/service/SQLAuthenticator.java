package fsc.service;

import fsc.entity.PasswordRecord;
import fsc.entity.session.Session;
import fsc.entity.session.UnauthenticatedSession;
import fsc.gateway.PasswordGateway;

public class SQLAuthenticator implements Authenticator {

  private final SessionCreator sessionCreator;
  private PasswordGateway gateway;

  public SQLAuthenticator(PasswordGateway gateway, SessionCreator sessionCreator) {
    this.gateway = gateway;
    this.sessionCreator = sessionCreator;
  }

  public Session authenticateWithCredentials(Credentials credentials) {
    try {
      PasswordRecord passwordRecord = gateway.getPasswordRecordFor(credentials.getUsername());
      boolean authenticated = passwordRecord.matchesPassword(credentials.getPassword());
      if (authenticated) {
        return sessionCreator.createSession(passwordRecord.getRole(), passwordRecord.getUsername());
      }
    } catch (PasswordGateway.UnknownUsernameException ignored) { }
    return new UnauthenticatedSession();
  }

}
