package fsc.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VoteTest {
  private Ballot ballot;

  @Before
  public void setup() {
    ballot = new Ballot();
  }

  @Test
  public void emptyBallotVoteTest() {
    Vote vote = new Vote(ballot);
  }

  @Test
  public void voteForOnePerson() {
    Profile candidate = new Profile("Sam", "sam55", "Art", "tenured");
    ballot.add(candidate);
    Vote vote = new Vote(ballot);
    vote.addSingleVote(candidate);
    List<Profile> expectedList = new ArrayList<>();
    expectedList.add(candidate);
    assertEquals(expectedList, vote.getRankedList());
  }

  @Test
  public void voteForMoreThanOnePerson() {
    Profile candidate1 = new Profile("Sam", "sam55", "Art", "tenured");
    Profile candidate2 = new Profile("Bill Maywood", "maywoodb", "SCI", "Tenured");
    Profile candidate3 = new Profile("Emma Joppins", "joppinse", "HUM", "Untenured");

    ballot.add(candidate1);
    ballot.add(candidate2);
    ballot.add(candidate3);

    Vote vote = new Vote(ballot);
    ArrayList<Profile> voteList = new ArrayList<>();
    voteList.add(candidate3);
    voteList.add(candidate2);

    vote.addMultipleVote(voteList);
    assertEquals(voteList, vote.getRankedList());
  }

  @Test
  public void removeOnePerson() {
    Profile candidate1 = new Profile("Sam", "sam55", "Art", "tenured");
    Profile candidate2 = new Profile("Bill Maywood", "maywoodb", "SCI", "Tenured");
    Profile candidate3 = new Profile("Emma Joppins", "joppinse", "HUM", "Untenured");

    ballot.add(candidate1);
    ballot.add(candidate2);
    ballot.add(candidate3);

    Vote vote = new Vote(ballot);
    ArrayList<Profile> voteList = new ArrayList<>();
    voteList.add(candidate3);
    voteList.add(candidate2);

    vote.addMultipleVote(voteList);
    vote.removeProfileFromVote(candidate2);

    List<Profile> expectedList = new ArrayList<>();
    expectedList.add(candidate3);
    assertEquals(expectedList, vote.getRankedList());
  }

  @Test
  public void removeMultiplePeople() {
    Profile candidate1 = new Profile("Sam", "sam55", "Art", "tenured");
    Profile candidate2 = new Profile("Bill Maywood", "maywoodb", "SCI", "Tenured");
    Profile candidate3 = new Profile("Emma Joppins", "joppinse", "HUM", "Untenured");

    ballot.add(candidate1);
    ballot.add(candidate2);
    ballot.add(candidate3);

    Vote vote = new Vote(ballot);
    ArrayList<Profile> voteList = new ArrayList<>();
    voteList.add(candidate3);
    voteList.add(candidate2);
    voteList.add(candidate1);

    vote.addMultipleVote(voteList);
    voteList.remove(candidate1);
    vote.removeMultipleVotes(voteList);

    List<Profile> expectedList = new ArrayList<>();
    expectedList.add(candidate1);
    assertEquals(expectedList, vote.getRankedList());
  }
}
