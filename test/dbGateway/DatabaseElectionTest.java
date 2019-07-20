package dbGateway;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.ElectionGateway;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

public class DatabaseElectionTest extends BasicDatabaseTest {
  private Election election;

  @Test
  public void canCreateAndSaveElection() throws ElectionGateway.InvalidElectionIDException {
    saveTheElection();
    assertNotNull(election.getID());
    Election anotherGatewayElection = anotherGateway.getElection(this.election.getID());
    assertNotNull(anotherGatewayElection);
  }

  @Test
  public void canAddACandidate() throws ElectionGateway.InvalidElectionIDException {
    saveTheElection();
    gateway.begin();
    Profile profile = gateway.getEntityFactory()
                             .createProfile("Haris Skiadas", "skiadas",
                                            "Natural Sciences", "tenured");
    gateway.addProfile(profile);
    Candidate candidate = gateway.getEntityFactory().createCandidate(profile, election);
    election.addCandidate(candidate);
    gateway.save();
    gateway.commit();
    Election anotherGatewayElection = anotherGateway.getElection(this.election.getID());
    assertEquals(1, anotherGatewayElection.getCandidates().size());
    assertThat(anotherGatewayElection.getCandidateProfiles(), hasItem(profile));
    for (Candidate candidate1 : anotherGatewayElection.getCandidates()) {
      assertEquals(candidate, candidate1);
    }
  }

  @Test
  public void canAddAVoter()
        throws ElectionGateway.InvalidElectionIDException, ElectionGateway.InvalidVoterException {
    saveTheElection();
    Voter voter = saveAVoter();
    Election anotherGatewayElection = anotherGateway.getElection(election.getID());
    assertEquals(1, anotherGatewayElection.getVoters().size());
    assertEquals(voter, anotherGateway.getVoter(voter.getVoterId()));
    for (Voter voter1 : anotherGatewayElection.getVoters()) {
      assertEquals(voter,  voter1);
      assertEquals(election, voter1.getElection());
      assertEquals(false, voter1.hasVoted());
    }
  }

  @Test
  public void canAddAVoteRecord() throws ElectionGateway.NoVoteRecordException {
    saveTheElection();
    Voter voter = saveAVoter();
    VoteRecord voteRecord = castAVote(voter);
    assertNotNull(voteRecord.getRecordId());
    VoteRecord voteRecord1 = anotherGateway.getVoteRecord(voteRecord.getRecordId());
    assertEquals(voteRecord, voteRecord1);
  }

  public Voter saveAVoter() {
    gateway.begin();
    Profile profile = gateway.getEntityFactory()
                             .createProfile("Haris Skiadas", "skiadas",
                                            "Natural Sciences", "tenured");
    gateway.addProfile(profile);
    Voter voter = gateway.getEntityFactory().createVoter(profile, election);
    gateway.addVoter(voter);
    gateway.save();
    gateway.commit();
    return voter;
  }

  private VoteRecord castAVote(Voter voter) {
    gateway.begin();
    VoteRecord voteRecord = gateway.getEntityFactory()
                                   .createVoteRecord(election, List.of(voter.getProfile()));
    gateway.addVoteRecord(voteRecord);
    gateway.save();
    gateway.commit();
    return voteRecord;


  }

  private void saveTheElection() {
    Committee committee = gateway.getEntityFactory()
                                 .createCommittee("committee name",
                                                  "committee description",
                                                  Query.always());
    Seat seat = gateway.getEntityFactory().createSeat("seat name", Query.always(),
                                                      committee);
    election = gateway.getEntityFactory().createElection(seat);
    gateway.addCommittee(committee);
    gateway.addSeat(seat);
    gateway.addElection(election);
    gateway.save();
    gateway.commit();
  }
}