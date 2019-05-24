package fsc.mock;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;

public class ElectionGatewaySpy implements ElectionGateway{
  public Election addedElection;
  public boolean hasSaved = false;

  public void save() {
    hasSaved = true;
  }

  public void addElection(Election election) {
    this.addedElection = election;
    addedElection.setID(1);
    hasSaved = false;
  }

  public void recordVote(VoteRecord voteRecord) {}

  public Election getElectionFromElectionID(String electionID) {
    return null;
  }

}
