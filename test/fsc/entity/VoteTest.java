package fsc.entity;

import fsc.mock.EntityStub;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class VoteTest {
  private List<Candidate> candidates;
  private Election election;
  private Candidate candidate1;
  private Candidate candidate2;
  private Candidate candidate3;
  private Vote vote;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    election = EntityStub.simpleElectionWithCandidates();
    candidates = election.getCandidates();
    candidate1 = addCandidate(0);
    candidate2 = addCandidate(1);
    candidate3 = addCandidate(2);
  }

  public Candidate addCandidate(int i) {
    Candidate candidate = entityFactory.createCandidate(EntityStub.getProfile(i),
                                                        election);
    candidates.add(candidate);
    return candidate;
  }

  @Test
  public void voteForOnePerson() {
    vote = createVote(candidate1);
    assertEquals(Candidate.toProfiles(List.of(candidate1)), vote.order);
  }

  @Test
  public void voteForMoreThanOnePerson() {
    vote = createVote(candidate3, candidate2);
    assertEquals(Candidate.toProfiles(List.of(candidate3, candidate2)), vote.order);
  }

  public Vote createVote(Candidate... candidates) {
    return entityFactory.createVote(Candidate.toProfiles(List.of(candidates)));
  }

  @Test
  public void removeOnePerson() {
    vote = createVote(candidate3);
    vote.remove(candidate2.getProfile());
    assertEquals(Candidate.toProfiles(List.of(candidate3)), vote.order);
  }

  @Test
  public void removeMultiplePeople() {
    vote = createVote(candidate3, candidate2, candidate1);
    vote.removeAll(Candidate.toProfiles(List.of(candidate3, candidate2)));
    assertEquals(Candidate.toProfiles(List.of(candidate1)), vote.order);
  }
}
