package fsc.mock.gateway.election;

import fsc.entity.Election;
import fsc.entity.VoteRecord;
import fsc.gateway.ElectionGateway;

public class AddedElectionGatewaySpy implements ElectionGateway {
  public Election addedElection;
  public boolean hasSaved = false;
  private String electionId;

  public AddedElectionGatewaySpy(String electionId) {
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

  public void recordVote(VoteRecord voteRecord) {}

  public boolean hasVoteRecord(String username, String electionID) {
    return false;
  }

  public Election getElection(String electionID) {
    return null;
  }

}
