package fsc.gateway;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.entity.VoteRecord;

import java.util.List;

public interface ElectionGateway {
  void save();
  void addElection(Election election);
  void recordVote(VoteRecord voteRecord);
  boolean hasVoteRecord(Profile voter, Election election);
  VoteRecord getVoteRecord(Profile voter, Election election) throws NoVoteRecordException;
  Election getElection(String electionID) throws InvalidElectionIDException;
  List<VoteRecord> getAllVotes(Election election);
  class InvalidElectionIDException extends Exception {
    public InvalidElectionIDException() {}
  }

  class NoVoteRecordException extends Exception {
  }
}
