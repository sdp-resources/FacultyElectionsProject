package fsc.gateway;

import fsc.entity.*;

public interface ElectionGateway {
  void save();
  void addElection(Election election);
  void recordVote(VoteRecord voteRecord);
  Election getElectionFromElectionID(String electionID) throws Exception;
}
