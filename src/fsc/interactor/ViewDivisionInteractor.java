package fsc.interactor;


import fsc.gateway.DivisionGateway;
import fsc.request.ViewDivisionRequest;
import fsc.response.Response;
import fsc.response.ViewDivisionResponse;

import java.util.List;

public class ViewDivisionInteractor {

  private DivisionGateway gateway;

  public ViewDivisionInteractor(DivisionGateway gateway){
    this.gateway = gateway;
  }

  public Response execute(ViewDivisionRequest request){
    String divisionName = request.divisionName;
    List<String> listOfDivisions = gateway.getAvailableDivisions();
    return new ViewDivisionResponse(listOfDivisions);
  }

}

