package fsc.interactor;

import fsc.gateway.CommitteeGateway;
import fsc.request.EditCommitteeRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public class EditCommitteeInteractor {
  public EditCommitteeInteractor(CommitteeGateway gateway) {

  }

  public Response execute(EditCommitteeRequest request) {
    return new ErrorResponse("No committee with that name");
  }
}
