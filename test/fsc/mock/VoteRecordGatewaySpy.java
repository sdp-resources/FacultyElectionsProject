package fsc.mock;

import fsc.entity.VoteRecord;
import fsc.gateway.VoteRecordGateway;

public class VoteRecordGatewaySpy implements VoteRecordGateway {

  public VoteRecord getVoteRecord() {
    return null;
  }
}
