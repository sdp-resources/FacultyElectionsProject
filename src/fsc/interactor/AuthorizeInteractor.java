package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.request.Request;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public class AuthorizeInteractor extends Interactor {
  private SessionGateway sessionGateway;

  public AuthorizeInteractor(SessionGateway sessionGateway) {
    this.sessionGateway = sessionGateway;
  }

  public boolean canHandle(Request request) {
    return true;
  }

  public Response execute(Request request) {
    return ErrorResponse.notAuthorized();
  }

}
