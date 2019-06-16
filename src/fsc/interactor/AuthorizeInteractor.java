package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.request.Request;
import fsc.response.Response;
import fsc.response.ResponseFactory;

public class AuthorizeInteractor {
  private SessionGateway sessionGateway;

  public AuthorizeInteractor(SessionGateway sessionGateway) {
    this.sessionGateway = sessionGateway;
  }

  public boolean canHandle(Request request) {
    return true;
  }

  public Response execute(Request request) {
    return ResponseFactory.notAuthorized();
  }

}
