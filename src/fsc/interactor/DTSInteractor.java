package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.request.DTSRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class DTSInteractor {

  private ElectionGateway electionGateway;
  public Profile profile;
  public Ballot ballot;
  public Candidate candidate;

  public DTSInteractor(ElectionGateway electionGateway) {
    this.electionGateway = electionGateway;
  }

  public Response execute(DTSRequest request) {
    try {
      getProperCandidate(request);
      if (request.status == Candidate.Status.Accepted) {
        candidate.setAccepted();
      } else {
        candidate.setDeclined();
      }
      electionGateway.save();
      return new SuccessResponse();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ErrorResponse.unknownElectionID();
    }
  }

  private void getProperCandidate(DTSRequest request)
        throws ElectionGateway.InvalidElectionIDException {
    Election election = electionGateway.getElection(request.electionID);
    Ballot ballot = election.getBallot();
    int candidateListSize = ballot.sizeCandidates();
    for (int candidateIndex = 0; candidateIndex < candidateListSize; candidateIndex++) {
      if (ballot.getCandidate(candidateIndex).getProfile() == profile) {
        candidate = ballot.getCandidate(candidateIndex);
      }
    }
  }
}