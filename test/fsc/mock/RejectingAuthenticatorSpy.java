package fsc.mock;

import fsc.entity.session.Session;
import fsc.entity.session.UnauthenticatedSession;
import fsc.service.Authenticator;
import fsc.service.Credentials;

public class RejectingAuthenticatorSpy implements Authenticator {
  public Session authenticateWithCredentials(Credentials credentials) {
    return new UnauthenticatedSession();
  }
}
