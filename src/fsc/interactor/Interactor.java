package fsc.interactor;

import fsc.request.Request;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public abstract class Interactor<T extends Request> {
  public abstract boolean canHandle(Request request);
  public abstract Response execute(T request);

  public Interactor<? extends Request> next = null;

  public Response handle(Request request) {
    return canHandle(request) ? execute((T) request) : passOn(request);
  }

  public Response passOn(Request request) {
    return next == null ? ErrorResponse.cannotHandle() : next.handle(request);
  }

  public Interactor<? extends Request> append(Interactor<? extends Request> other) {
    next = next == null ? other : next.append(other);
    return this;
  }
}
