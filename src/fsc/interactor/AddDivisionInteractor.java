package fsc.interactor;

import fsc.gateway.DivisionGateway;
import fsc.request.AddDivisionRequest;
import fsc.response.FailedtoAddDivision;
import fsc.response.SuccessfullyAddedDivision;
import fsc.response.Response;

public class AddDivisionInteractor {

  public static DivisionGateway gateway;

  public AddDivisionInteractor(DivisionGateway gateway) {
    this.gateway = gateway;
  }

  public static Response execute(AddDivisionRequest request) throws Exception {
    if (gateway.hasDivision(request.name)){
      return new FailedtoAddDivision();
    }
    gateway.addDivision(request.name);
    gateway.save();
    return new SuccessfullyAddedDivision();
  }
}
