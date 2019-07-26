package fsc.interactor;

import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;
import fsc.gateway.SessionGateway;
import fsc.request.LoginRequest;
import fsc.response.*;
import fsc.service.Authenticator;
import fsc.service.Credentials;

public class LoginInteractor extends Interactor {
  private SessionGateway sessionGateway;
  private Authenticator authenticator;

  LoginInteractor(SessionGateway sessionGateway, Authenticator authenticator) {
    this.sessionGateway = sessionGateway;
    this.authenticator = authenticator;
  }

  public Response execute(LoginRequest request) {
    Credentials credentials = new Credentials(request.username, request.password);
    Session session = authenticator.authenticateWithCredentials(credentials);
    if (!session.isAuthenticated()) {
      return ResponseFactory.invalidCredentials();
    }
    AuthenticatedSession authenticatedSession = (AuthenticatedSession) session;
    sessionGateway.addSession(authenticatedSession);
    sessionGateway.save();
    return ResponseFactory.ofAuthenticatedSession(authenticatedSession);
  }

}
