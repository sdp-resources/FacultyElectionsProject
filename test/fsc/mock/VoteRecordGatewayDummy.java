package fsc.mock;

import fsc.entity.VoteRecord;
import fsc.gateway.VoteRecordGateway;

public class VoteRecordGatewayDummy implements VoteRecordGateway {

  public VoteRecord getVoteRecord() {
    return null;
  }
}
