package fsc.entity;

import fsc.gateway.ProfileGateway;
import fsc.mock.AlwaysFalseQueryStub;
import fsc.mock.AlwaysTrueQueryStub;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BallotCreatorTest {

  private BallotCreator ballotCreator;
  private ProfileGateway mockGateway;

  @Before
  public void setUp() throws Exception {
    ballotCreator = new BallotCreator();
    mockGateway = makeGateway();
    ballotCreator.setProfileGateway(mockGateway);
  }

  @Test
  public void whenGivenAlwaysFalseQuery_GetEmptyBallot() {
    Ballot ballot = ballotCreator.getBallot(new AlwaysFalseQueryStub());
    assertTrue(ballot.isEmpty());
  }

  @Test
  public void whenGivenAlwaysTrueQuery_AddAllProfilesToBallot() {
    Ballot ballot = ballotCreator.getBallot(new AlwaysTrueQueryStub());
    assertSameLists(ballot, mockGateway.getAllProfiles());
  }

  private void assertSameLists(List list1, List<Profile> list2) {
    for (Profile profile : list2) {
      assertTrue(list1.contains(profile));
    }
    assertEquals(list2.size(), list1.size());
  }

  private ProfileGateway makeGateway() {
    return new MockGateway();
  }

  private class MockGateway implements ProfileGateway {
    Profile profile1 = new Profile("name1", "username1", "dept", "contract");
    Profile profile2 = new Profile("name2", "username2", "dept", "contract");
    Profile profile3 = new Profile("name3", "username3", "dept", "contract");
    private ArrayList<Profile> allProfiles = new ArrayList<>();

    public MockGateway() {
      allProfiles.add(profile1);
      allProfiles.add(profile2);
      allProfiles.add(profile3);
    }

    public Profile getProfile(String username) {
      return null;
    }

    public List<Profile> getAllProfiles() {
      return allProfiles;
    }

    public void addProfile(Profile profile) { }

    public boolean isValidDivision(String division) {
      return false;
    }

    public void saveProfile(Profile profile) {

    }

  }
}
