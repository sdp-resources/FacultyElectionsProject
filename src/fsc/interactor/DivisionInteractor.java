package fsc.interactor;

import fsc.gateway.DivisionGateway;
import fsc.request.AddDivisionRequest;
import fsc.response.FailedtoAddDivision;
import fsc.response.SuccessfullyAddedDivision;
import fsc.response.Response;

public class DivisionInteractor {

  public static DivisionGateway gateway;

  public DivisionInteractor(DivisionGateway gateway) {
    this.gateway = gateway;
  }

  public static Response execute(AddDivisionRequest request) throws Exception {
    try { gateway.getDivision(request.name);}
    catch (Exception e) {
      gateway.addDivision(request.name);
      return new SuccessfullyAddedDivision();
    }
    return new FailedtoAddDivision();
  }
}
