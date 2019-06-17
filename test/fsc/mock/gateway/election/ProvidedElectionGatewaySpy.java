package fsc.mock.gateway.election;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;

import java.util.ArrayList;
import java.util.List;

public class ProvidedElectionGatewaySpy implements ElectionGateway {
  public boolean hasSaved;
  private Election storedElection;
  public String providedElectionId = null;
  public VoteRecord submittedVoteRecord = null;
  private List<VoteRecord> voteRecords = new ArrayList<>();

  public ProvidedElectionGatewaySpy(Election election) {
    storedElection = election;
  }

  public void save() { hasSaved = true; }

  public void addElection(Election election) {

  }

  public void recordVote(VoteRecord voteRecord) {
    submittedVoteRecord = voteRecord;
    voteRecords.add(voteRecord);
  }

  public boolean hasVoteRecord(Profile voter, Election election) {
    return submittedVoteRecord != null && submittedVoteRecord.isRecordFor(voter, election);
  }

  public VoteRecord getVoteRecord(Profile voter, Election election) throws NoVoteRecordException {
    if (hasVoteRecord(voter, election)) {
      return submittedVoteRecord;
    }
    throw new NoVoteRecordException();
  }

  public Election getElection(String electionID) {
    providedElectionId = electionID;
    return storedElection;
  }

  public List<VoteRecord> getAllVotes(Election election) {
    return voteRecords;
  }
}
