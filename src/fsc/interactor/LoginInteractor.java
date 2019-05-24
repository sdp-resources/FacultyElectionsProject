package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.SessionGateway;
import fsc.request.LoginRequest;
import fsc.response.ErrorResponse;
import fsc.response.LoginResponse;
import fsc.response.Response;
import fsc.service.Authenticator;
import fsc.service.Authorizer;

public class LoginInteractor {
  private final SessionGateway sessionGateway;
  private final Authorizer authorizer;
  private final Authenticator authenticator;

  public LoginInteractor(SessionGateway sessionGateway, Authorizer authorizer, Authenticator authenticator) {
    this.sessionGateway = sessionGateway;
    this.authorizer = authorizer;
    this.authenticator = authenticator;
  }

  public Response execute(LoginRequest request) {
    Session authorization = authorizer.authorize(request.username, request.password);
    if (!authorization.isAuthorized())
    {
      return new ErrorResponse("Invalid username or password");
    }
    sessionGateway.addSession(new AuthorizedSession(((AuthorizedSession) authorization).getRole(),
                                                    ((AuthorizedSession) authorization).getUsername(),
                                                    ((AuthorizedSession) authorization).getToken(),
                                                    ((AuthorizedSession) authorization).getExpirationTime()));
    sessionGateway.save();
    return new LoginResponse(((AuthorizedSession) authorization).getRole(),
                             ((AuthorizedSession) authorization).getToken());
  }
}
