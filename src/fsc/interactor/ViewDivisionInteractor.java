package fsc.interactor;


import fsc.gateway.DivisionGateway;
import fsc.request.ViewDivisionRequest;
import fsc.response.Response;
import fsc.response.ViewDivisionResponse;

public class ViewDivisionInteractor {

  private DivisionGateway gateway;

  public ViewDivisionInteractor(DivisionGateway gateway){
    this.gateway = gateway;
  }

  public Response execute(ViewDivisionRequest request){
    gateway.getAvailableDivisions();
    return new ViewDivisionResponse();
  }

}

