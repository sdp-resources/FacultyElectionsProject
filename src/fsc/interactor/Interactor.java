package fsc.interactor;

import fsc.request.Request;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public abstract class Interactor {
  public abstract boolean canHandle(Request request);
  public abstract Response execute(Request request);

  private Interactor next = null;

  public Response handle(Request request) {
    return canHandle(request) ? execute(request) : passOn(request);
  }

  public Response passOn(Request request) {
    return next == null ? ErrorResponse.cannotHandle() : next.handle(request);
  }

  public Interactor prependTo(Interactor other) {
    next = other;
    return this;
  }
}
