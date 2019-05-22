package fsc.gateway;

import fsc.entity.VoteRecord;

import fsc.entity.*;

public interface ElectionGateway {
  Committee getCommitteeFromCommitteeName(String committeeName) throws InvalidCommitteeNameException;
  class InvalidSeatNameException extends Exception {}
  class InvalidCommitteeNameException extends Exception {}
  void save();
  void addElection(Election election);
  void recordVote(VoteRecord voteRecord);

}
