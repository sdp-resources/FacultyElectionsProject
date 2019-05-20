package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VoteTest {
  Ballot ballot;

  @Before
  private Ballot setup() {
    return new Ballot();
  }

  @Test
  public void emptyBallotVoteTest(){
    Vote vote = new Vote(ballot);
  }

  @Test
  public void voteForOnePerson(){
    Profile profile = new Profile("Sam", "sam55", "Art", "tenured");
    ballot.add(profile);
    Vote vote = new Vote(ballot);
    vote.addSingleVote(profile);
    List<Profile> expectedList = new ArrayList<>();
    expectedList.add(profile);
    assertEquals(expectedList, vote.getRankedList());
    ballot.clear();
  }

  @Test
  public void voteForMoreThanOnePerson() {
    ballot.add(new Profile("Sam", "sam55", "Art", "tenured"));
    ballot.add(new Profile("Bill Maywood", "maywoodb", "SCI", "Tenured"));
    ballot.add(new Profile("Emma Joppins", "joppinse", "HUM", "Untenured"));

    Vote vote = new Vote(ballot);
    //vote
  }



}
