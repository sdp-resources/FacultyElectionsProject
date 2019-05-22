package fsc.interactor;

import fsc.entity.Authorization;
import fsc.entity.AuthorizedAuthorization;
import fsc.entity.Session;
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
    Authorization authorization = authorizer.authorize(request.username, request.password);
    if (!authorization.isAuthorized())
    {
      return new ErrorResponse("Invalid username or password");
    }
    AuthorizedAuthorization authorizedAuthorization = (AuthorizedAuthorization) authorization;
    sessionGateway.addSession(new Session(authorizedAuthorization.role,
                                          authorizedAuthorization.username,
                                          authorizedAuthorization.token,
                                          authorizedAuthorization.expirationTime));
    sessionGateway.save();
    return new LoginResponse(authorizedAuthorization.role, authorizedAuthorization.token);
  }
}
