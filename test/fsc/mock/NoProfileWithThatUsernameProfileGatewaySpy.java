package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;
import java.util.List;

public class NoProfileWithThatUsernameProfileGatewaySpy implements ProfileGateway {
  public final ArrayList<Profile> profileList;
  public String submittedUsername;

  public NoProfileWithThatUsernameProfileGatewaySpy(){
    this.profileList = null;
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    submittedUsername = username;
    throw new InvalidProfileUsernameException();
  }

  public void addProfile(Profile profile) { }

  public void save() {
  }

  public List<Profile> getAllProfiles() {
    return null;
  }

  public boolean isValidDivision(String division) {
    return true;
  }

}

