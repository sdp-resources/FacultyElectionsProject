package fsc.interactor;

import fsc.gateway.Gateway;
import fsc.request.Request;
import fsc.request.RequestVisitor;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.AuthorizingRequestVisitor;

public class AuthorizingInteractor extends Interactor {
  private RequestVisitor authorizer;

  public AuthorizingInteractor(Gateway gateway) {
    authorizer = new AuthorizingRequestVisitor(gateway);
  }

  @Override
  public <T extends Request> Response handle(T request) {
    if (isAuthorized(request)) {
      return delegate(request);
    }
    return ResponseFactory.notAuthorized();
  }

  private boolean isAuthorized(Request request) {
    return (boolean) request.accept(authorizer);
  }

}
