package fsc.interactor;

import fsc.gateway.BallotGateway;
import fsc.request.SeeElectionCandidatesRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public class SeeElectionCandidatesInteractor {
  public SeeElectionCandidatesInteractor(BallotGateway gateway) {}

  public Response execute(SeeElectionCandidatesRequest request) {
    return new ErrorResponse("There are no candidates");
  }
}
