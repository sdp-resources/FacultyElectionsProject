package fsc.mock;

import fsc.entity.VoteRecord;
import fsc.gateway.VoteRecordGateway;

public class VoteRecordGatewaySpy implements VoteRecordGateway {
  public String username = "wilsonT";
  public Boolean boolTestVar = false;
  public VoteRecord voteRecord;

  public void recordVote(VoteRecord voteRecord) {
    boolTestVar = true;
    this.voteRecord = voteRecord;
  }
}
