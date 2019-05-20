package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VoteTest {
  Ballot ballot;

  @Before
  public void setup() {
    ballot = new Ballot();
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
  }

  @Test
  public void voteForMoreThanOnePerson() {
    Profile profile1 = new Profile("Sam", "sam55", "Art", "tenured");
    Profile profile2 = new Profile("Bill Maywood", "maywoodb", "SCI", "Tenured");
    Profile profile3 = new Profile("Emma Joppins", "joppinse", "HUM", "Untenured");

    ballot.add(profile1);
    ballot.add(profile2);
    ballot.add(profile3);

    Vote vote = new Vote(ballot);
    ArrayList<Profile> voteList = new ArrayList<>();
    voteList.add(profile3);
    voteList.add(profile2);

    vote.addMultipleVote(voteList);
    assertEquals(voteList, vote.getRankedList());
  }

  @Test
  public void removeOnePerson() {
    Profile profile1 = new Profile("Sam", "sam55", "Art", "tenured");
    Profile profile2 = new Profile("Bill Maywood", "maywoodb", "SCI", "Tenured");
    Profile profile3 = new Profile("Emma Joppins", "joppinse", "HUM", "Untenured");

    ballot.add(profile1);
    ballot.add(profile2);
    ballot.add(profile3);

    Vote vote = new Vote(ballot);
    ArrayList<Profile> voteList = new ArrayList<>();
    voteList.add(profile3);
    voteList.add(profile2);

    vote.addMultipleVote(voteList);
    vote.removeProfileFromVote(profile2);

    List<Profile> expectedList = new ArrayList<>();
    expectedList.add(profile3);
    assertEquals(expectedList, vote.getRankedList());
  }

  @Test
  public void removeMultiplePeople(){
    Profile profile1 = new Profile("Sam", "sam55", "Art", "tenured");
    Profile profile2 = new Profile("Bill Maywood", "maywoodb", "SCI", "Tenured");
    Profile profile3 = new Profile("Emma Joppins", "joppinse", "HUM", "Untenured");

    ballot.add(profile1);
    ballot.add(profile2);
    ballot.add(profile3);

    Vote vote = new Vote(ballot);
    ArrayList<Profile> voteList = new ArrayList<>();
    voteList.add(profile3);
    voteList.add(profile2);
    voteList.add(profile1);

    vote.addMultipleVote(voteList);
    voteList.remove(profile1);
    vote.removeMultipleVotes(voteList);

    List<Profile> expectedList = new ArrayList<>();
    expectedList.add(profile1);
    assertEquals(expectedList, vote.getRankedList());
  }
}
