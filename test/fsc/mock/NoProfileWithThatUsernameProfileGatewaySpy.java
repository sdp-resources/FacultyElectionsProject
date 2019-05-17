package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;
import java.util.List;

public class NoProfileWithThatUsernameProfileGatewaySpy implements ProfileGateway {
  public ArrayList<Profile> profileList;
  public String submittedUsername;

  public NoProfileWithThatUsernameProfileGatewaySpy(){
    this.profileList = null;
  }



  public ArrayList<Profile> getProfiles() {
    return null;
  }

  public Profile getProfileFromUsername(String username) throws InvalidProfileUsernameException {
    submittedUsername = username;
    throw new InvalidProfileUsernameException();
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public List<Profile> getAllProfiles() {
    return null;
  }

  public void clearProfileList() {

  }

  public boolean isValidDivision(String division) {
    return true;
  }

  public void updateProfile(Profile profile) {

  }
}

