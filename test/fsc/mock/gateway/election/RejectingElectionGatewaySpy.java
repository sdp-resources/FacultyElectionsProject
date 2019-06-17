package fsc.mock.gateway.election;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.entity.VoteRecord;
import fsc.gateway.ElectionGateway;

public class RejectingElectionGatewaySpy implements ElectionGateway {
  public String requestedElectionId;

  public void save() { }

  public void addElection(Election election) { }

  public void recordVote(VoteRecord voteRecord) {}

  public boolean hasVoteRecord(String username, String electionID) {
    return false;
  }

  public VoteRecord getVoteRecord(Profile voter, Election election) throws NoVoteRecordException {
    throw new NoVoteRecordException();
  }

  public Election getElection(String electionID) throws InvalidElectionIDException {
    requestedElectionId = electionID;
    throw new InvalidElectionIDException();
  }

  public boolean hasVoteRecord(Profile voter, Election election) {
    return hasVoteRecord(voter.getUsername(), election.getID());
  }
}
