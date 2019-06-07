package fsc.entity;

import fsc.mock.EntityStub;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class VoteTest {
  private Ballot ballot;
  private Election election;
  private Profile candidate1;
  private Profile candidate2;
  private Profile candidate3;
  private Vote vote;

  @Before
  public void setup() {
    election = EntityStub.simpleBallotElection();
    ballot = election.getBallot();
    candidate1 = EntityStub.getProfile(0);
    candidate2 = EntityStub.getProfile(1);
    candidate3 = EntityStub.getProfile(2);
    ballot.addAll(List.of(candidate1, candidate2, candidate3));
    vote = new Vote();
  }

  @Test
  public void voteForOnePerson() {
    vote.addVotes(List.of(candidate1));
    assertEquals(List.of(candidate1), vote.getRankedList());
  }

  @Test
  public void voteForMoreThanOnePerson() {
    vote.addVotes(List.of(candidate3, candidate2));
    assertEquals(List.of(candidate3, candidate2), vote.getRankedList());
  }

  @Test
  public void removeOnePerson() {
    vote.addVotes(List.of(candidate3, candidate2));
    vote.removeProfileFromVote(candidate2);
    assertEquals(List.of(candidate3), vote.getRankedList());
  }

  @Test
  public void removeMultiplePeople() {
    vote.addVotes(List.of(candidate3, candidate2, candidate1));
    vote.removeMultipleVotes(List.of(candidate3, candidate2));
    assertEquals(List.of(candidate1), vote.getRankedList());
  }
}
