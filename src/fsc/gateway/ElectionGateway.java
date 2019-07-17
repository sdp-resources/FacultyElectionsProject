package fsc.gateway;

import fsc.entity.*;

import java.util.Collection;
import java.util.List;

public interface ElectionGateway {
  void save();
  void addElection(Election election);
  void addVoteRecord(VoteRecord voteRecord);
  VoteRecord getVoteRecord(long recordId) throws NoVoteRecordException;
  Election getElection(String electionID) throws InvalidElectionIDException;
  List<VoteRecord> getAllVotes(Election election);
  Collection<Election> getAllElections();
  Voter getVoter(Profile profile, Election election) throws InvalidVoterException;
  class InvalidElectionIDException extends Exception {
    public InvalidElectionIDException() {}
  }

  class NoVoteRecordException extends Exception {}

  class InvalidVoterException extends Exception {}
}
