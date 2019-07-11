package fsc.gateway;

import fsc.entity.*;

import java.util.Collection;
import java.util.List;

public interface ElectionGateway {
  void save();
  void addElection(Election election);
  void recordVote(VoteRecord voteRecord);
  boolean hasVoteRecord(Voter voter);
  VoteRecord getVoteRecord(Voter voter) throws NoVoteRecordException;
  Election getElection(String electionID) throws InvalidElectionIDException;
  List<VoteRecord> getAllVotes(Election election);
  Collection<Election> getAllElections();
  class InvalidElectionIDException extends Exception {
    public InvalidElectionIDException() {}
  }

  class NoVoteRecordException extends Exception {
  }
}
