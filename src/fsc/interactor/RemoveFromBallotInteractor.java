package fsc.interactor;

import fsc.gateway.BallotGateway;
import fsc.gateway.ProfileGateway;
import fsc.request.RemoveFromBallotRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public class RemoveFromBallotInteractor {
  public RemoveFromBallotInteractor(
        BallotGateway ballotGateway, ProfileGateway profileGateway) {

  }

  public Response execute(RemoveFromBallotRequest request) {
    return new ErrorResponse("No ballot with that ID");
  }
}
