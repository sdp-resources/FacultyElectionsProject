package fsc.interactor;

import fsc.request.Request;
import fsc.response.Response;
import fsc.response.ResponseFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Interactor {
  public Response execute(Request request) {
    return ResponseFactory.cannotHandle();
  }

  public Interactor next = null;

  public boolean canHandle(Request request) {
    try {
      getExecuteForRequest(request);
      return true;
    } catch (NoSuchMethodException e) {
      return false;
    }
  }

  public Response handle(Request request) {
    return canHandle(request) ? callAppropriateExecute(request)
                              : passOn(request);
  }

  private Response callAppropriateExecute(Request request) {
    try {
      return (Response) getExecuteForRequest(request).invoke(this, request);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("Should really not be happening" + e.getMessage());
    } catch (InvocationTargetException e) {
      if (e.getTargetException() instanceof RuntimeException) {
        throw (RuntimeException) e.getTargetException();
      }
      throw new RuntimeException(e.getTargetException());
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("Should not be happening:" + e.getMessage());
    }
  }

  public Response passOn(Request request) {
    return next == null ? ResponseFactory.cannotHandle() : next.handle(request);
  }

  private Method getExecuteForRequest(Request request) throws NoSuchMethodException {
    return this.getClass().getDeclaredMethod("execute", request.getClass());
  }

  public Interactor append(Interactor other) {
    next = next == null ? other : next.append(other);
    return this;
  }
}
