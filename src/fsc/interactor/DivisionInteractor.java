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
    try { gateway.getDivisionWithName(request.name);}
    catch (Exception e) {
      return new SuccessfullyAddedDivision();
    }
    return new FailedtoAddDivision();
  }
}
