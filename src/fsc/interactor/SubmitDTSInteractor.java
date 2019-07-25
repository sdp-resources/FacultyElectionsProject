package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.request.DTSRequest;
import fsc.response.*;

import java.util.Collection;

public class SubmitDTSInteractor extends Interactor {

  private ElectionGateway electionGateway;
  public Profile profile;
  public Collection<Candidate> ballot;
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
      return ResponseFactory.success();
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ResponseFactory.unknownElectionID();
    } catch (ElectionGateway.NoProfileInBallotException e) {
      return ResponseFactory.invalidCandidate();
    }
  }

}