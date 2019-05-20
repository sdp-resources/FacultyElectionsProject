package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.request.ViewCandidatesRequest;
import fsc.response.*;
import fsc.viewable.ViewableProfile;

import java.util.ArrayList;
import java.util.List;

public class ViewCandidatesInteractor {
  private final BallotGateway gateway;

  public ViewCandidatesInteractor(BallotGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(ViewCandidatesRequest request) throws Exception {
    if (gateway.getBallot(request.electionID) == null) {
      return new ErrorResponse("There are no candidates");
    }
    ViewCandidates(request);
    return new SuccessfullyViewedCandidatesResponse();
  }

  private List<ViewableProfile> ViewCandidates(ViewCandidatesRequest request) throws BallotGateway.InvalidBallotIDException {
    Ballot ballot = gateway.getBallot(request.electionID);
    List<ViewableProfile> viewableCandidates = new ArrayList<>();
    for (Profile profile : ballot) {

    }


    return viewableCandidates;
  }
}
