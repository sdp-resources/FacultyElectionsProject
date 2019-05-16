package fsc.interactor;

import fsc.gateway.DivisionGateway;
import fsc.gateway.Gateway;
import fsc.request.AddDivisionRequest;

public class DivisionInteractor {

  private final DivisionGateway gateway;

  public DivisionInteractor(DivisionGateway gateway) {
    this.gateway = gateway;
  }

  //public void execute(AddDivisionRequest request) throws Exception {
    //try (gateway.getDivisionWithName(request.name))
      //catch
  //}
}
