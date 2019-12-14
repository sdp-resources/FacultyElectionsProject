package fsc.voting;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class VoteTest {
  private VoteTarget candidate1;
  private VoteTarget candidate2;
  private VoteTarget candidate3;
  private Vote vote;

  @Before
  public void setup() {
    candidate1 = addCandidate(0);
    candidate2 = addCandidate(1);
    candidate3 = addCandidate(2);
  }

  public VoteTarget addCandidate(int i) {
    return VoteTarget.from("candidate" + i);
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
    vote = Vote.of(candidate3);
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
