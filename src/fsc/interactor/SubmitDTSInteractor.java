package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.request.DTSRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

public class SubmitDTSInteractor {

  private ElectionGateway electionGateway;
  public Profile profile;
  public Ballot ballot;
  public Candidate candidate;

  public SubmitDTSInteractor(ElectionGateway electionGateway) {
    this.electionGateway = electionGateway;
  }

  public Response execute(DTSRequest request) {
    try {
      Election election = electionGateway.getElection(request.electionID);
      candidate = election.getCandidateByUsername(request.userName);
      candidate.setStatus(request.status);
      electionGateway.save();
      return new SuccessResponse();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ErrorResponse.unknownElectionID();
    } catch (Ballot.NoProfileInBallotException e) {
      return ErrorResponse.invalidCandidate();
    }
  }

}