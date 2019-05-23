package fsc.interactor;

import fsc.gateway.DivisionGateway;
import fsc.request.AddDivisionRequest;
import fsc.request.Request;
import fsc.response.*;

public class AddDivisionInteractor implements Interactor {

  public static DivisionGateway gateway;

  public AddDivisionInteractor(DivisionGateway gateway) {
    this.gateway = gateway;
  }

  public static Response execute(AddDivisionRequest request) {
    if (gateway.hasDivision(request.name)){
      return new ErrorResponse("Division already exists");
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
