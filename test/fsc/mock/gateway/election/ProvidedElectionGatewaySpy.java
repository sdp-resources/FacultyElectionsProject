package fsc.mock.gateway.election;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProvidedElectionGatewaySpy implements ElectionGateway {
  public boolean hasSaved;
  private Election storedElection;
  public String providedElectionId = null;
  public VoteRecord submittedVoteRecord = null;
  private List<VoteRecord> voteRecords = new ArrayList<>();
  private EntityFactory entityFactory = new SimpleEntityFactory();

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

  public boolean hasVoteRecord(Voter voter) {
    return submittedVoteRecord != null && submittedVoteRecord.isRecordFor(
          entityFactory.createVoter(voter.getVoter(), voter.getElection()));
  }

  public VoteRecord getVoteRecord(Voter voter) throws NoVoteRecordException {
    if (hasVoteRecord(entityFactory.createVoter(voter.getVoter(), voter.getElection()))) {
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

  public Collection<Election> getAllElections() {
    return null;
  }
}
