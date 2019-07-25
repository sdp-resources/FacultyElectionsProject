package fsc.interactor;

import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;
import fsc.gateway.SessionGateway;
import fsc.request.LoginRequest;
import fsc.response.*;
import fsc.service.Authenticator;
import fsc.service.Authorizer;

public class LoginInteractor extends Interactor {
  private SessionGateway sessionGateway;
  private Authorizer authorizer;
  private Authenticator authenticator;

  LoginInteractor(
        SessionGateway sessionGateway, Authorizer authorizer, Authenticator authenticator
  ) {
    this.sessionGateway = sessionGateway;
    this.authorizer = authorizer;
    this.authenticator = authenticator;
  }

  public Response execute(LoginRequest request) {
    Session authorization = authorizer.authorize(request.username, request.password);
    if (!authorization.isAuthorized()) {
      return ResponseFactory.notAuthorized();
    }
    sessionGateway.addSession(new AuthenticatedSession(((AuthenticatedSession) authorization).getRole(),
                                                       ((AuthenticatedSession) authorization)
                                                          .getUsername(),
                                                       ((AuthenticatedSession) authorization).getToken(),
                                                       ((AuthenticatedSession) authorization)
                                                          .getExpirationTime()));
    sessionGateway.save();
    return new LoginResponse(((AuthenticatedSession) authorization).getRole().toString(),
                             ((AuthenticatedSession) authorization).getToken());
  }

}
