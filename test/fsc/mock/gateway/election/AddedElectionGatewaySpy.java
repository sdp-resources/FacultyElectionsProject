package fsc.mock.gateway.election;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;

import java.util.Collection;
import java.util.List;

public class AddedElectionGatewaySpy implements ElectionGateway {
  public Election addedElection;
  public boolean hasSaved = false;
  private Long electionId;

  public AddedElectionGatewaySpy(long electionId) {
    this.electionId = electionId;
  }

  public void save() {
    hasSaved = true;
  }

  public void addElection(Election election) {
    this.addedElection = election;
    election.setID(electionId);
    hasSaved = false;
  }

  public void addVoteRecord(VoteRecord voteRecord) {}

  public VoteRecord getVoteRecord(long recordId) throws NoVoteRecordException {
    throw new NoVoteRecordException();
  }

  public Election getElection(long electionID) throws InvalidElectionIDException {
    throw new InvalidElectionIDException();
  }

  public List<VoteRecord> getAllVotes(Election election) {
    return null;
  }

  public Collection<Election> getAllElections() {
    return null;
  }

  public Voter getVoter(long voterId) {
    return null;
  }

  public void addVoter(Voter voter) { }

}
