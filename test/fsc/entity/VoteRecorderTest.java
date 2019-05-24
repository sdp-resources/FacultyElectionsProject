package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VoteRecorderTest {

  private String vote;

  @Before
  public void setUp() {
    Ballot ballot = new Ballot();
    vote = "";
  }

  @Test
  public void getProfileTest(){
  Profile profile = new Profile ("Dan Smith", "DanSmith3", "Com", "Tenured");
  VoteRecord voteRecord = new VoteRecord(profile, vote, 1);
  assertEquals(profile, voteRecord.getProfile());
  }

  @Test
  public void getVoteTest(){
    Profile profile = new Profile ("Dan Smith", "DanSmith3", "Com", "Tenured");
    VoteRecord voteRecord = new VoteRecord(profile, vote, 1);
    assertEquals(vote, voteRecord.getVote());
  }

  @Test
  public void getCalendarTest(){
    Profile profile = new Profile ("Dan Smith", "DanSmith3", "Com", "Tenured");
    Date expectedDate = Calendar.getInstance().getTime();
    VoteRecord voteRecord = new VoteRecord(profile, vote, 1);
    long diffInMillies = Math.abs(expectedDate.getTime() - voteRecord.getDate().getTime());
    assertTrue(diffInMillies < 2000);
  }

  @Test
  public void getElectionIDTest(){
    Profile profile = new Profile ("Dan Smith", "DanSmith3", "Com", "Tenured");
    Date expectedDate = Calendar.getInstance().getTime();
    VoteRecord voteRecord = new VoteRecord(profile, vote, 1);
    assertEquals(1, voteRecord.getElectionID());
  }

}
