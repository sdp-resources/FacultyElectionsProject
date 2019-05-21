package fsc.gateway;

import fsc.entity.VoteRecord;

public interface VoteRecordGateway {
  public void recordVote(VoteRecord voteRecord);
}
