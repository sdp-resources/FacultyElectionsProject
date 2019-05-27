package fsc.mock.gateway.election;

import fsc.entity.Election;
import fsc.entity.VoteRecord;
import fsc.gateway.ElectionGateway;

public class RejectingElectionGatewaySpy implements ElectionGateway {
  public String requestedElectionId;

  public void save() { }

  public void addElection(Election election) { }

  public void recordVote(VoteRecord voteRecord) {}

  public Election getElection(String electionID) throws InvalidElectionIDException {
    requestedElectionId = electionID;
    throw new InvalidElectionIDException();
  }

}
