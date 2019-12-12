package fsc.app;

import fsc.entity.session.Session;
import fsc.entity.session.UnauthenticatedSession;
import fsc.service.*;

public class DelegatingAuthenticator implements Authenticator {
  private Authenticator[] authenticators;

  public DelegatingAuthenticator(Authenticator ...authenticators) {

    this.authenticators = authenticators;
  }

  public Session authenticateWithCredentials(Credentials credentials) {
    for (Authenticator authenticator : authenticators) {
      Session session = authenticator.authenticateWithCredentials(credentials);
      if (session.isAuthenticated()) { return session; }
    }
    return new UnauthenticatedSession();
  }
}
