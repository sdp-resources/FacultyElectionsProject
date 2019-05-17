package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;

public class InvalidDivisionProfileGatewaySpy implements ProfileGateway {
  public ArrayList<Profile> profileList;
  public String submittedUsername;
  public String submittedDivision;

  public InvalidDivisionProfileGatewaySpy(){
    this.profileList = null;
  }


  public ArrayList<Profile> getAllProfiles() {
    return null;
  }

  public Profile getProfileWithUsername(String username) throws Exception {
    submittedUsername = username;
    throw new Exception("No profile1 with that name Here");
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public void clearProfileList() {}

  public boolean isValidDivision(String division) {
    submittedDivision = division;
    return false;
  }

  public void saveProfile(Profile profile) {

  }

  public Profile getProfileFromUsername(String userName) {
    return null;
  }

}