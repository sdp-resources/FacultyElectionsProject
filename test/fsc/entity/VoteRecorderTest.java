package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VoteRecorderTest {

  private Ballot ballot;
  private Vote vote;

  @Before
  public void setUp() throws Exception {
    ballot = new Ballot();
    vote = new Vote(ballot);
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
    assertTrue(voteRecord.getDate().after(expectedDate));
  }

  @Test
  public void getElectionIDTest(){
    Profile profile = new Profile ("Dan Smith", "DanSmith3", "Com", "Tenured");
    Date expectedDate = Calendar.getInstance().getTime();
    VoteRecord voteRecord = new VoteRecord(profile, vote, 1);
    assertEquals(1, voteRecord.getElectionID());
  }



}