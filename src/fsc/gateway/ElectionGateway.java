package fsc.gateway;

import fsc.entity.Election;
import fsc.entity.VoteRecord;

public interface ElectionGateway {
  void save();
  void addElection(Election election);
  void recordVote(VoteRecord voteRecord);
  Election getElection(String electionID) throws InvalidElectionIDException;
  class InvalidElectionIDException extends Exception {
    public InvalidElectionIDException() {}
  }
}
