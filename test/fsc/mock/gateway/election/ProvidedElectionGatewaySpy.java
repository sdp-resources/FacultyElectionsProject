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

  public void addVoteRecord(VoteRecord voteRecord) {
    submittedVoteRecord = voteRecord;
    voteRecords.add(voteRecord);
  }

  public VoteRecord getVoteRecord(long recordId) throws NoVoteRecordException {
    if (submittedVoteRecord != null && submittedVoteRecord.getRecordId().equals(recordId)) {
      return submittedVoteRecord;
    }
    throw new  NoVoteRecordException();
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

  public Voter getVoter(Profile profile, Election election) throws InvalidVoterException {
    Voter voter = election.getVoter(profile);
    if (voter == null) {
      throw new InvalidVoterException();
    }
    return voter;
  }
}
