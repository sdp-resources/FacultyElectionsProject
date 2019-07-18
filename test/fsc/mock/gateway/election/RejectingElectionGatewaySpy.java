package fsc.mock.gateway.election;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;

import java.util.Collection;
import java.util.List;

public class RejectingElectionGatewaySpy implements ElectionGateway {
  public String requestedElectionId;
  public boolean hasSaved = false;

  public void save() {
    hasSaved = true;
  }

  public void addElection(Election election) { }

  public void addVoteRecord(VoteRecord voteRecord) {}

  public VoteRecord getVoteRecord(long recordId) throws NoVoteRecordException {
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

  public Voter getVoter(long voterId) throws InvalidVoterException {
    return null;
  }

  public void addVoter(Voter voter) { }

}
