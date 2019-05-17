package fsc.entity;

import fsc.gateway.Gateway;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElectionCreatorTest {
  private ElectionCreator electionCreator;
  private Gateway mockGateway;

  @Before
  public void setUp() {
    electionCreator = new ElectionCreator();
    mockGateway = makeGateway();
    electionCreator.setGateway(mockGateway);
  }

  @Test
  public void electionDoesExist(){
    Election election = new Election();
    assertTrue(election.doesExist());
  }

  @Test
  public void canCreateElection(){
    ElectionCreator election = new ElectionCreator();
    election.add("m", "o");
    /// create getCommittee, getSeat etc.
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

    public Profile getProfileFromUsername(String username) {
      return null;
    }

    public List<Profile> getAllProfiles() {
      return allProfiles;
    }

    public Profile addProfile(Profile profile) {
      return null;
    }

    public boolean isValidDivision(String division) {
      return false;
    }

    public void saveProfile(Profile profile) {

    }

    public String addContractType(String string) {
      return null;
    }

    public void getContractTypeFromProfile(String contract_type) {

    }
  }

}