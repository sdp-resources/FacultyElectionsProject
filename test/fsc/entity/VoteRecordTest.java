package fsc.entity;

import fsc.mock.EntityStub;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VoteRecordTest {

  private List<Profile> votes;
  private Profile voter;
  private VoteRecord voteRecord;
  private Election election;

  @Before
  public void setUp() {
    votes = List.of(EntityStub.getProfile(1), EntityStub.getProfile(2));
    voter = EntityStub.getProfile(0);
    election = EntityStub.simpleBallotElection();
    voteRecord = new VoteRecord(voter, votes, election);
  }

  @Test
  public void voteRecordCreatedProperly() {
    assertEquals(voter, voteRecord.getVoter());
    assertEquals(votes, voteRecord.getVotes());
    assertCloseDates(LocalDateTime.now(), voteRecord.getDate());
    assertEquals(election, voteRecord.getElection());
  }

  public static void assertCloseDates(LocalDateTime date1, LocalDateTime date2) {
    assertTrue(date1.until(date2, MILLIS) < 2000);
  }

}
