package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.request.ViewDTSRequest;
import fsc.response.*;

public class ViewDTSInteractor {

  private ElectionGateway electionGateway;
  public Profile profile;
  public Ballot ballot;
  public Candidate candidate;

  public ViewDTSInteractor(ElectionGateway electionGateway) {
    this.electionGateway = electionGateway;
  }

  public Response execute(ViewDTSRequest request) {
    try {
      Election election = electionGateway.getElection(request.electionID);
      candidate = election.getCandidateByUsername(request.username);
      return ResponseFactory.ofCandidate(candidate);
    } catch (ElectionGateway.InvalidElectionIDException e) {
      return ResponseFactory.unknownElectionID();
    } catch (Ballot.NoProfileInBallotException e) {
      return ResponseFactory.invalidCandidate();
    }
  }
}
