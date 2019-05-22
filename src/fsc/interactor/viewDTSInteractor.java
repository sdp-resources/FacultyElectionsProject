package fsc.interactor;

import fsc.gateway.Gateway;
import fsc.request.viewDTSRequest;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import fsc.response.viewDTSResponse;

public class viewDTSInteractor {

  public Gateway gateway;

  public viewDTSInteractor(Gateway gateway){
    this.gateway = gateway;
  }

  public Response execute(viewDTSRequest request) {
    Response response = new SuccessResponse();
    return response;
  }
}
