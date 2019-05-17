package fsc.interactor;

import fsc.gateway.ProfileGateway;
import fsc.request.ViewProfilesListRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public class ViewProfilesListInteractor {
  public ViewProfilesListInteractor(ProfileGateway profileGateway) {

  }

  public Response execute(ViewProfilesListRequest request) {
    return new ErrorResponse("Not implemented");
  }
}
