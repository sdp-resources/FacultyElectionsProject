package fsc.gateway;

import fsc.entity.*;

import java.util.Collection;

public interface ElectionGateway {
  void save();
  void addElection(Election election);
  void addVoteRecord(VoteRecord voteRecord);
  VoteRecord getVoteRecord(long recordId) throws NoVoteRecordException;
  Election getElection(long electionID) throws InvalidElectionIDException;
  Collection<VoteRecord> getAllVotes(Election election);
  Collection<Election> getAllElections();
  Voter getVoter(long voterId) throws InvalidVoterException;
  void addVoter(Voter voter) throws ExistingVoterException;
  void removeCandidate(Candidate candidate);
  class InvalidElectionIDException extends Exception {
    public InvalidElectionIDException() {}
  }

  class NoVoteRecordException extends Exception {}

  class InvalidVoterException extends Exception {}

  class ExistingVoterException extends Exception { }

  class NoProfileInBallotException extends Exception {}
}
