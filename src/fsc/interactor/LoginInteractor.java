package fsc.interactor;

import fsc.entity.session.AuthorizedSession;
import fsc.entity.session.Session;
import fsc.gateway.SessionGateway;
import fsc.request.LoginRequest;
import fsc.response.*;
import fsc.service.Authenticator;
import fsc.service.Authorizer;

public class LoginInteractor {
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
    sessionGateway.addSession(new AuthorizedSession(((AuthorizedSession) authorization).getRole(),
                                                    ((AuthorizedSession) authorization)
                                                          .getUsername(),
                                                    ((AuthorizedSession) authorization).getToken(),
                                                    ((AuthorizedSession) authorization)
                                                          .getExpirationTime()));
    sessionGateway.save();
    return new LoginResponse(((AuthorizedSession) authorization).getRole().toString(),
                             ((AuthorizedSession) authorization).getToken());
  }

}
