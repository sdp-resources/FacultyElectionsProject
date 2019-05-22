package fsc.interactor;

import fsc.entity.Authorization;
import fsc.entity.Authorized;
import fsc.gateway.SessionGateway;
import fsc.request.LoginRequest;
import fsc.response.ErrorResponse;
import fsc.response.LoginResponse;
import fsc.response.Response;
import fsc.service.Authorizer;

public class LoginInteractor {
  private Authorizer authorizer;

  public LoginInteractor(SessionGateway sessionGateway, Authorizer authorizer) {
    this.authorizer = authorizer;
  }

  public Response execute(LoginRequest request) {
    Authorization authorization = authorizer.authorize(request.username, request.password);
    if (!authorization.isAuthorized())
    {
      return new ErrorResponse("Invalid username or password");
    }
    Authorized authorized = (Authorized) authorization;
    return new LoginResponse(authorized.role, authorized.token);
  }
}
