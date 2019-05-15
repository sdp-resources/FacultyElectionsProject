package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;

public class ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy implements ProfileGateway {
  public static ArrayList<Profile> profileList;
  public static String submittedUsername;
  public static Profile dummyProfile = new Profile("a","b","c","d");

  public ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(){
    this.profileList = null;
  }


  public ArrayList<Profile> getAllProfiles() {
    return null;
  }

  public Profile getProfileFromUsername(String username) {
    submittedUsername = username;
    return dummyProfile;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public Boolean isValidDivision(String division) {
    return true;
  }

  public Profile getProfile(String userName) {
    return null;
  }
}