package fsc.gateway;

import fsc.entity.VoteRecord;

public interface VoteRecordGateway {
  void recordVote(VoteRecord voteRecord);
}
