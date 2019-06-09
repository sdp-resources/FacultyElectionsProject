package fsc.interactor;

import fsc.gateway.DivisionGateway;
import fsc.request.AddDivisionRequest;
import fsc.request.Request;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class AddDivisionInteractor extends Interactor<AddDivisionRequest> {

  private DivisionGateway gateway;

  public AddDivisionInteractor(DivisionGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(AddDivisionRequest request) {
    if (gateway.hasDivision(request.name)) {
      return ErrorResponse.resourceExists();
    }
    gateway.addDivision(request.name);
    gateway.save();
    return new SuccessResponse();
  }

  public boolean canHandle(Request request) {
    return request instanceof AddDivisionRequest;
  }
}
