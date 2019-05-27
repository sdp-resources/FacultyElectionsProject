package fsc.mock.gateway.election;

import fsc.entity.Election;
import fsc.entity.VoteRecord;
import fsc.gateway.ElectionGateway;

public class ProvidedElectionGatewaySpy implements ElectionGateway {
  public boolean hasSaved;
  private Election storedElection;
  public String providedElectionId = null;

  public ProvidedElectionGatewaySpy(Election election) {
    storedElection = election;
  }

  public void save() { hasSaved = true; }

  public void addElection(Election election) {

  }

  public void recordVote(VoteRecord voteRecord) {

  }

  public Election getElection(String electionID) throws InvalidElectionIDException {
    providedElectionId = electionID;
    return storedElection;
  }
}
