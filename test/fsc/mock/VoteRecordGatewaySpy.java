package fsc.mock;

import fsc.entity.VoteRecord;
import fsc.gateway.VoteRecordGateway;

public class VoteRecordGatewaySpy implements VoteRecordGateway {
  public String username = "wilsonT";
  public Boolean boolTestVar = false;

  public VoteRecord recordVote() {
    boolTestVar = true;
    return null;
  }
}
