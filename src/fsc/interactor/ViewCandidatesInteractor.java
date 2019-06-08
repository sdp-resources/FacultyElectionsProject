package fsc.interactor;

import fsc.entity.Election;
import fsc.gateway.ElectionGateway;
import fsc.request.ViewCandidatesRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ViewResponse;

class ViewCandidatesInteractor {
  private ElectionGateway electionGateway;

  ViewCandidatesInteractor(ElectionGateway electionGateway) {
    this.electionGateway = electionGateway;
  }

  public Response execute(ViewCandidatesRequest request) {
    try {
      Election election = electionGateway.getElection(request.electionID);
      return ViewResponse.ofProfileList(election.getCandidateProfiles());
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ErrorResponse.unknownElectionID();
    }
  }

}
