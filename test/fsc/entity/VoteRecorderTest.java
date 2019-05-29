package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VoteRecorderTest {

  private String vote;
  private Profile profile;

  @Before
  public void setUp() {
    Ballot ballot = new Ballot();
    vote = "";
    profile = new Profile("Dan Smith", "DanSmith3", "Com", "Tenured");
  }

  @Test
  public void getProfileTest() {
    VoteRecord voteRecord = new VoteRecord(profile, vote, 1);
    assertEquals(profile, voteRecord.getProfile());
  }

  @Test
  public void getVoteTest() {
    VoteRecord voteRecord = new VoteRecord(profile, vote, 1);
    assertEquals(vote, voteRecord.getVote());
  }

  @Test
  public void getCalendarTest() {
    Date expectedDate = Calendar.getInstance().getTime();
    VoteRecord voteRecord = new VoteRecord(profile, vote, 1);
    long diffInMillies = Math.abs(expectedDate.getTime() - voteRecord.getDate().getTime());
    assertTrue(diffInMillies < 2000);
  }

  @Test
  public void getElectionIDTest() {
    Date expectedDate = Calendar.getInstance().getTime();
    VoteRecord voteRecord = new VoteRecord(profile, vote, 1);
    assertEquals(1, voteRecord.getElectionID());
  }

}
