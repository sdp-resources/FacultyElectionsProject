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
    ballot.addCandidates(List.of(candidate1, candidate2, candidate3));
  }

  @Test
  public void voteForOnePerson() {
    vote = Vote.of(candidate1);
    assertEquals(List.of(candidate1), vote.order);
  }

  @Test
  public void voteForMoreThanOnePerson() {
    vote = Vote.of(candidate3, candidate2);
    assertEquals(List.of(candidate3, candidate2), vote.order);
  }

  @Test
  public void removeOnePerson() {
    vote = Vote.of(candidate3, candidate2);
    vote.remove(candidate2);
    assertEquals(List.of(candidate3), vote.order);
  }

  @Test
  public void removeMultiplePeople() {
    vote = Vote.of(candidate3, candidate2, candidate1);
    vote.removeAll(List.of(candidate3, candidate2));
    assertEquals(List.of(candidate1), vote.order);
  }
}
