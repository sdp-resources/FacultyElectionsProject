package fsc.entity;

import fsc.mock.EntityStub;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    Date now = Calendar.getInstance().getTime();
    assertCloseDates(now, voteRecord.getDate());
    assertEquals(election, voteRecord.getElection());
  }

  public static void assertCloseDates(Date date1, Date date2) {
    long diffInMillies = Math.abs(date1.getTime() - date2.getTime());
    assertTrue(diffInMillies < 2000);
  }

}
