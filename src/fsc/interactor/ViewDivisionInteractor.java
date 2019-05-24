package fsc.interactor;


import fsc.gateway.DivisionGateway;
import fsc.request.ViewDivisionRequest;
import fsc.response.Response;
import fsc.response.ViewDivisionResponse;

import java.util.List;

public class ViewDivisionInteractor {

  private final DivisionGateway gateway;

  public ViewDivisionInteractor(DivisionGateway gateway){
    this.gateway = gateway;
  }

  public Response execute(ViewDivisionRequest request){
    List<String> listOfDivisions = gateway.getAvailableDivisions();
    return new ViewDivisionResponse(listOfDivisions);
  }

}

