package fsc.interactor;

import fsc.request.Request;
import fsc.response.Response;

public interface Interactor {
  boolean canHandle(Request request);
  Response execute(Request request);
}
