package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.SessionGateway;
import fsc.request.LoginRequest;
import fsc.response.ErrorResponse;
import fsc.response.LoginResponse;
import fsc.response.Response;
import fsc.service.Authorizer;

public class LoginInteractor {
  private SessionGateway sessionGateway;
  private Authorizer authorizer;

  public LoginInteractor(SessionGateway sessionGateway, Authorizer authorizer) {
    this.sessionGateway = sessionGateway;
    this.authorizer = authorizer;
  }

  public Response execute(LoginRequest request) {
    Session authorization = authorizer.authorize(request.username, request.password);
    if (!authorization.isAuthorized())
    {
      return new ErrorResponse("Invalid username or password");
    }
    Session authorizedSession = (AuthorizedSession) authorization;
    sessionGateway.addSession(new AuthorizedSession(((AuthorizedSession) authorizedSession).getRole(),
                                                    ((AuthorizedSession) authorizedSession).getUsername(),
                                                    ((AuthorizedSession) authorizedSession).getToken(),
                                                    ((AuthorizedSession) authorizedSession).getExpirationTime()));
    sessionGateway.save();
    return new LoginResponse(((AuthorizedSession) authorizedSession).getRole(),
                             ((AuthorizedSession) authorizedSession).getToken());
  }
}
