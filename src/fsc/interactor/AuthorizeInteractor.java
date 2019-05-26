package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.request.Request;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public class AuthorizeInteractor implements Interactor {
  private final SessionGateway sessionGateway;

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
