package fsc.interactor;

import fsc.gateway.DivisionGateway;
import fsc.request.AddDivisionRequest;
import fsc.request.Request;
import fsc.response.*;

public class AddDivisionInteractor implements Interactor {

  private DivisionGateway gateway;

  public AddDivisionInteractor(DivisionGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(AddDivisionRequest request) {
    if (gateway.hasDivision(request.name)){
      return ErrorResponse.resourceExists();
    }
    gateway.addDivision(request.name);
    gateway.save();
    return new SuccessfullyAddedDivision();
  }

  public boolean canHandle(Request request) {
    return request instanceof AddDivisionRequest;
  }

  public Response execute(Request request) {
    return execute((AddDivisionRequest) request);
  }
}
