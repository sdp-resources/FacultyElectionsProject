package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.request.LoginRequest;
import fsc.response.ErrorResponse;
import fsc.response.LoginResponse;
import fsc.response.Response;
import fsc.service.Authorizer;

public class LoginInteractor {
  public LoginInteractor(SessionGateway sessionGateway, Authorizer authorizer) {

  }

  public Response execute(LoginRequest request) {
    return new ErrorResponse("Invalid username or password");
  }
}
