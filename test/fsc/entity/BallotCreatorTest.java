package fsc.entity;

import fsc.gateway.Gateway;
import fsc.entity.query.AlwaysFalseQuery;
import fsc.entity.query.AlwaysTrueQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BallotCreatorTest {

  private BallotCreator ballotCreator;
  private Gateway mockGateway;

  @Before
  public void setUp() throws Exception {
    ballotCreator = new BallotCreator();
    mockGateway = makeGateway();
    ballotCreator.setGateway(mockGateway);
  }

  @Test
  public void whenGivenAlwaysFalseQuery_GetEmptyBallot(){
    Ballot ballot = ballotCreator.getBallot(new AlwaysFalseQuery());
    assertTrue(ballot.isEmpty());
  }

  @Test
  public void whenGivenAlwaysTrueQuery_AddAllProfilesToBallot() {
    Ballot ballot = ballotCreator.getBallot(new AlwaysTrueQuery());
    assertSameLists(ballot, mockGateway.getAllProfiles());
  }

  private void assertSameLists(List list1, List<Profile> list2) {
    for (Profile profile : list2) {
      assertTrue(list1.contains(profile));
    }
    assertEquals(list2.size(), list1.size());
  }

  private Gateway makeGateway() {
    return new MockGateway();
  }

  private class MockGateway implements Gateway {
    Profile profile1 = new Profile("name1", "username1", "dept", "contract");
    Profile profile2 = new Profile("name2", "username2", "dept", "contract");
    Profile profile3 = new Profile("name3", "username3", "dept", "contract");
    private ArrayList<Profile> allProfiles = new ArrayList<>();

    public MockGateway() {
      allProfiles.add(profile1);
      allProfiles.add(profile2);
      allProfiles.add(profile3);
    }

    public List<Profile> getAllProfiles() {
      return allProfiles;
    }
  }
}
