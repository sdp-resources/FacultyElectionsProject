package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.BallotGateway;
import fsc.request.ViewCandidatesRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ViewResponse;
import fsc.viewable.ViewableProfile;

import java.util.ArrayList;
import java.util.List;

class ViewCandidatesInteractor {
  private BallotGateway gateway;

  ViewCandidatesInteractor(BallotGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(ViewCandidatesRequest request) {
    try {
      Ballot ballot = gateway.getBallot(request.electionID);
      List<ViewableProfile> viewableCandidates = convertBallotToListOfViewableProfiles(ballot);
      return new ViewResponse(viewableCandidates);
    } catch (BallotGateway.InvalidBallotIDException e) {
      return ErrorResponse.unknownElectionID();
    }
  }

  private List<ViewableProfile> convertBallotToListOfViewableProfiles(Ballot ballot) {
    List<ViewableProfile> viewableCandidates = new ArrayList<>();
    for (Profile profile : ballot) {
      viewableCandidates.add(ViewableProfile.from(profile));
    }
    return viewableCandidates;
  }

}
