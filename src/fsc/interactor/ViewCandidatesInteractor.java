package fsc.interactor;

import fsc.gateway.BallotGateway;
import fsc.request.ViewCandidatesRequest;
import fsc.response.*;

public class ViewCandidatesInteractor {
  private final BallotGateway gateway;

  public ViewCandidatesInteractor(BallotGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(ViewCandidatesRequest request) throws Exception {
    if (gateway.getBallot(request.electionID) == null) {
      return new ErrorResponse("There are no candidates");
    }
    //ViewCandidates();
    return new SuccessfullyViewedCandidatesResponse();
  }
}
