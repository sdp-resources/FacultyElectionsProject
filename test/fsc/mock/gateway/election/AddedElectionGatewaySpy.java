package fsc.mock.gateway.election;

import fsc.entity.Election;
import fsc.entity.VoteRecord;
import fsc.gateway.ElectionGateway;

public class AddedElectionGatewaySpy implements ElectionGateway {
  public Election addedElection;
  public boolean hasSaved = false;

  public void save() {
    hasSaved = true;
  }

  public void addElection(Election election) {
    this.addedElection = election;
    hasSaved = false;
  }

  public void recordVote(VoteRecord voteRecord) {}

  public Election getElection(String electionID) {
    return null;
  }

}
