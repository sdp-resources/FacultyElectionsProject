package fsc.mock;

import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;
import fsc.service.*;

public class AcceptingAuthenticatorSpy implements Authenticator {
  public String token;

  public Session authenticateWithCredentials(Credentials credentials) {
    AuthenticatedSession session = new SessionCreator().createSession(
          Authorizer.Role.ROLE_ADMIN,
          credentials.getUsername()
    );
    token = session.getToken();
    return session;
  }
}
