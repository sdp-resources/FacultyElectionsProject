package fsc.service;

import fsc.interactor.Interactor;
import fsc.request.Request;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public class RequestRouter {
  private final Interactor[] interactors;

  public RequestRouter(Interactor... interactors)
  {
    this.interactors = interactors;
  }

  public Response execute(Request request) {
    for(Interactor interactor : interactors)
    {
      if (interactor.canHandle(request))
      {
        return interactor.execute(request);
      }
    }

    return new ErrorResponse("No interactor can handle that request");
  }
}
