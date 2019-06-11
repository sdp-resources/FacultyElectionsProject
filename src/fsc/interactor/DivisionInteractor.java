package fsc.interactor;

import fsc.gateway.DivisionGateway;
import fsc.request.AddDivisionRequest;
import fsc.request.ViewDivisionRequest;
import fsc.response.*;

public class DivisionInteractor extends Interactor {

  private DivisionGateway gateway;

  public DivisionInteractor(DivisionGateway gateway) {
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

  public Response execute(ViewDivisionRequest request) {
    return ViewResponse.ofStrings(gateway.getAvailableDivisions());
  }
}