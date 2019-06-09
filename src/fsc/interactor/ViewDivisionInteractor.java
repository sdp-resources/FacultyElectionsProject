package fsc.interactor;

import fsc.gateway.DivisionGateway;
import fsc.request.Request;
import fsc.request.ViewDivisionRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;

public class ViewDivisionInteractor extends Interactor<ViewDivisionRequest> {

  private DivisionGateway gateway;

  public ViewDivisionInteractor(DivisionGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(ViewDivisionRequest request) {
    return ViewResponse.ofStrings(gateway.getAvailableDivisions());
  }

  public boolean canHandle(Request request) {
    return request instanceof ViewDivisionRequest;
  }
}

