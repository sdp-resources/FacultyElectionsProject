package fsc.interactor;

import fsc.request.EditCommitteeRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public class EditCommitteeInteractor {
  public Response execute(EditCommitteeRequest request) {
    return new ErrorResponse("No committee with that name");
  }
}
