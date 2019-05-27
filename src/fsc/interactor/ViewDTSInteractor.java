package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.gateway.Gateway;
import fsc.gateway.ProfileGateway;
import fsc.request.ViewDTSRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ViewResponse;
import fsc.service.ViewableEntityConverter;

public class ViewDTSInteractor {

  private Gateway gateway;
  public Profile profile;
  public Ballot ballot;
  public Candidate candidate;

  public ViewDTSInteractor(Gateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(ViewDTSRequest request) {
    try {
      getProperCandidate(request);
      return new ViewResponse<>(new ViewableEntityConverter().convert(candidate));
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ErrorResponse.unknownElectionID();
    }
  }

  private void getProperCandidate(ViewDTSRequest request)
        throws ProfileGateway.InvalidProfileUsernameException,
               ElectionGateway.InvalidElectionIDException {
    Profile profile = gateway.getProfile(request.username);
    Election election = gateway.getElection(request.electionID);
    Ballot ballot = election.getBallot();
    int candidateListSize = ballot.sizeCandidates();
    for (int candidateIndex = 0; candidateIndex < candidateListSize; candidateIndex++) {
      if (ballot.getCandidate(candidateIndex).getProfile() == profile) {
        candidate = ballot.getCandidate(candidateIndex);
      }
    }
  }
}
