package fsc.mock;

import fsc.entity.Election;
import fsc.entity.VoteRecord;
import fsc.gateway.ElectionGateway;

public class VoteRecordGatewaySpy implements ElectionGateway {
  public String username = "wilsonT";
  public Boolean boolTestVar = false;
  public VoteRecord voteRecord;

  public void save() {}

  public void addElection(Election election) {}

  public void recordVote(VoteRecord voteRecord) {
    boolTestVar = true;
    this.voteRecord = voteRecord;
  }

  public Election getElectionFromElectionID(String electionID) {
    return null;
  }
}