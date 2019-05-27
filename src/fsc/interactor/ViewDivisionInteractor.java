package fsc.interactor;

import fsc.gateway.DivisionGateway;
import fsc.request.ViewDivisionRequest;
import fsc.response.ViewResponse;

import java.util.List;

public class ViewDivisionInteractor {

  private DivisionGateway gateway;

  public ViewDivisionInteractor(DivisionGateway gateway) {
    this.gateway = gateway;
  }

  public ViewResponse<List<String>> execute(ViewDivisionRequest request) {
    return ViewResponse.ofStrings(gateway.getAvailableDivisions());
  }

}

