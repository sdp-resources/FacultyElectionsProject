package fsc.mock;

import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;
import fsc.service.Authenticator;
import fsc.service.Authorizer;
import fsc.service.Credentials;

import java.time.LocalDateTime;

public class AcceptingAuthenticatorSpy implements Authenticator {
  public String token;

  public Session authenticateWithCredentials(Credentials credentials) {
    token = "a token";
    return new AuthenticatedSession(Authorizer.Role.ROLE_ADMIN,
                                    credentials.getUsername(),
                                    token,
                                    LocalDateTime.now().plusHours(1));
  }
}
