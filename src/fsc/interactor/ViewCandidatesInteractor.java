package fsc.interactor;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.gateway.ElectionGateway;
import fsc.request.ViewCandidatesRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ViewResponse;
import fsc.viewable.ViewableProfile;

import java.util.ArrayList;
import java.util.List;

class ViewCandidatesInteractor {
  private ElectionGateway electionGateway;

  ViewCandidatesInteractor(ElectionGateway electionGateway) {
    this.electionGateway = electionGateway;
  }

  public Response execute(ViewCandidatesRequest request) {
    try {
      Election election = electionGateway.getElection(request.electionID);
      List<ViewableProfile> viewableCandidates = getViewableCandidates(election);
      return new ViewResponse(viewableCandidates);
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ErrorResponse.unknownElectionID();
    }
  }

  private List<ViewableProfile> getViewableCandidates(Election election) {
    List<ViewableProfile> viewableCandidates = new ArrayList<>();
    for (Profile profile : election.getBallot()) {
      viewableCandidates.add(ViewableProfile.from(profile));
    }
    return viewableCandidates;
  }

}
