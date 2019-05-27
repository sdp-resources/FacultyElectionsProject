package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.gateway.Gateway;
import fsc.gateway.ProfileGateway;
import fsc.request.DTSRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class DTSInteractor {

  private Gateway gateway;
  public Profile profile;
  public Ballot ballot;
  public Candidate candidate;

  public DTSInteractor(Gateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(DTSRequest request) {
    try {
      getProperCandidate(request);
      if (request.status == Candidate.Status.Accepted) {
        candidate.setAccepted();
      } else {
        candidate.setDeclined();
      }
      gateway.save();
      return new SuccessResponse();
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ErrorResponse.unknownProfileName();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ErrorResponse.unknownElectionID();
    }
  }

  private void getProperCandidate(DTSRequest request)
        throws ProfileGateway.InvalidProfileUsernameException,
               ElectionGateway.InvalidElectionIDException {
    Profile profile = gateway.getProfile(request.userName);
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