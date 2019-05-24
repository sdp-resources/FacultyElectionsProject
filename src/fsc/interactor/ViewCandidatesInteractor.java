package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.request.ViewCandidatesRequest;
import fsc.response.*;
import fsc.viewable.ViewableProfile;

import java.util.ArrayList;
import java.util.List;

class ViewCandidatesInteractor {
  private final BallotGateway gateway;

  ViewCandidatesInteractor(BallotGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(ViewCandidatesRequest request) throws Exception {
    if (gateway.getBallot(request.electionID) == null) {
      return new ErrorResponse("Invalid Election ID");
    }
    return viewCandidates(request);
  }

  private Response viewCandidates(ViewCandidatesRequest request) throws BallotGateway.InvalidBallotIDException {
    Ballot ballot = gateway.getBallot(request.electionID);
    List<ViewableProfile> viewableCandidates = convertBallotToListOfViewableProfiles(ballot);
    return new SuccessfullyViewedCandidatesResponse(viewableCandidates);
  }

  private List<ViewableProfile> convertBallotToListOfViewableProfiles(Ballot ballot) {
    List<ViewableProfile> viewableCandidates = new ArrayList<>();
    for (Profile profile : ballot) {
      viewableCandidates.add(ViewableProfile.from(profile));
    }
    return viewableCandidates;
  }

}
