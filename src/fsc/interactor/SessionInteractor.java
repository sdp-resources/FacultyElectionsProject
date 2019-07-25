package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.request.Request;
import fsc.response.Response;
import fsc.response.ResponseFactory;

public class SessionInteractor extends Interactor {
  private SessionGateway sessionGateway;

  public SessionInteractor(SessionGateway sessionGateway) {
    this.sessionGateway = sessionGateway;
  }

  public Response handle(Request request) {
    return execute(request);
  }

  public Response execute(Request request) {
    return ResponseFactory.notAuthorized();
  }

}
