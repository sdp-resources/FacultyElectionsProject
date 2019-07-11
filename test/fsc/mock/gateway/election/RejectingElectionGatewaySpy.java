package fsc.mock.gateway.election;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;

import java.util.Collection;
import java.util.List;

public class RejectingElectionGatewaySpy implements ElectionGateway {
  public String requestedElectionId;

  public void save() { }

  public void addElection(Election election) { }

  public void recordVote(VoteRecord voteRecord) {}

  public boolean hasVoteRecord(String username, String electionID) {
    return false;
  }

  public VoteRecord getVoteRecord(Voter voter) throws NoVoteRecordException {
    throw new NoVoteRecordException();
  }

  public Election getElection(String electionID) throws InvalidElectionIDException {
    requestedElectionId = electionID;
    throw new InvalidElectionIDException();
  }

  public List<VoteRecord> getAllVotes(Election election) {
    return null;
  }

  public Collection<Election> getAllElections() {
    return null;
  }

  public boolean hasVoteRecord(Voter voter) {
    return hasVoteRecord(voter.getVoter().getUsername(), voter.getElection().getID());
  }
}
