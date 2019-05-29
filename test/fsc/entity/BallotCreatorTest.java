package fsc.entity;

import fsc.entity.query.FalseQuery;
import fsc.entity.query.TrueQuery;
import fsc.gateway.ProfileGateway;
import fsc.mock.EntityStub;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BallotCreatorTest {

  private BallotCreator ballotCreator;
  private ProfileGateway mockGateway;

  @Before
  public void setUp() {
    ballotCreator = new BallotCreator();
    mockGateway = new ProfileGatewayStub(EntityStub.getProfile(0), EntityStub.getProfile(1),
                                         EntityStub.getProfile(2));
    ballotCreator.setProfileGateway(mockGateway);
  }

  @Test
  public void whenGivenAlwaysFalseQuery_GetEmptyBallot() {
    Ballot ballot = ballotCreator.getBallot(new FalseQuery());
    assertTrue(ballot.isEmpty());
  }

  @Test
  public void whenGivenAlwaysTrueQuery_AddAllProfilesToBallot() {
    Ballot ballot = ballotCreator.getBallot(new TrueQuery());
    assertSameLists(ballot, mockGateway.getAllProfiles());
  }

  private void assertSameLists(List list1, List<Profile> list2) {
    for (Profile profile : list2) {
      assertTrue(list2.contains(profile));
    }
    assertEquals(list2.size(), list1.size());
  }

}
