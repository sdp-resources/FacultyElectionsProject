package fsc.entity;

import fsc.entity.query.Query;
import fsc.gateway.ProfileGateway;
import fsc.interactor.fetcher.ElectionFetcher;
import fsc.mock.EntityStub;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BallotCreatorTest {
  private ProfileGateway mockGateway;
  private SimpleEntityFactory entityFactory = new SimpleEntityFactory();
  private Election election = EntityStub.simpleElectionWithCandidates();
  private ElectionFetcher electionFetcher;

  @Before
  public void setUp() {
    mockGateway = new ProfileGatewayStub(EntityStub.getProfile(0), EntityStub.getProfile(1),
                                         EntityStub.getProfile(2));
    electionFetcher = new ElectionFetcher(null, mockGateway,
                                          null, entityFactory);
  }

  @Test
  public void whenGivenAlwaysFalseQuery_GetEmptyBallot() {
    election.setCandidateQuery(Query.never());

    electionFetcher.addValidCandidatesToElection(Query.never(), election);
    assertTrue(election.getCandidates().isEmpty());
  }

  @Test
  public void whenGivenAlwaysTrueQuery_AddAllProfilesToBallot() {

    electionFetcher.addValidCandidatesToElection(Query.always(), election);
    assertSameLists(election.getCandidates(), mockGateway.getAllProfiles());
  }

  private void assertSameLists(Collection<Candidate> list1, List<Profile> list2) {
    for (Candidate candidate: list1) {
      assertTrue(list2.contains(candidate.getProfile()));
    }
    assertEquals(list2.size(), list1.size());
  }

}
