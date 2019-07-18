package fsc.mock.gateway.election;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProvidedElectionGatewaySpy implements ElectionGateway {
  public static final int VOTER_ID = 7;
  private static final long RECORD_ID = 5;
  public boolean hasSaved;
  public Voter submittedVoter = null;
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

  public void addVoteRecord(VoteRecord voteRecord) {
    submittedVoteRecord = voteRecord;
    if (voteRecord.getRecordId() == null) { voteRecord.setRecordId(RECORD_ID); }
    voteRecords.add(voteRecord);
  }

  public VoteRecord getVoteRecord(long recordId) throws NoVoteRecordException {
    if (submittedVoteRecord != null && submittedVoteRecord.getRecordId().equals(recordId)) {
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

  public Voter getVoter(long voterId) throws InvalidVoterException {
    for (Voter voter : storedElection.getVoters()) {
      if (voter.getVoterId() == voterId) {
        return voter;
      }
    }
    throw new InvalidVoterException();
  }

  public void provideVoter(Voter voter) {
    storedElection.addVoter(voter);
  }

  public void addVoter(Voter voter) throws ExistingVoterException {
    if (voterExists(voter)) { throw new ElectionGateway.ExistingVoterException(); }
    submittedVoter = voter;
    voter.setVoterId(VOTER_ID);
    storedElection.addVoter(voter);
  }

  private boolean voterExists(Voter voter) {
    for (Voter storedVoter : storedElection.getVoters()) {
      if (storedVoter.getProfile().equals(voter.getProfile()) &&
                storedVoter.getElection().equals(voter.getElection())) {
        return true;
      }
    }

    return false;
  }
}
