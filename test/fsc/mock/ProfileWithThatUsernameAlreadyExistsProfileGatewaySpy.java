package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;

public class ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy implements ProfileGateway {
  public static ArrayList<Profile> profileList;
  public static String submittedUsername;
  public static Profile dummyProfile = new Profile("a","b","c","d");
  public Profile profileSent;

  public ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(){
    this.profileList = null;
  }


  public ArrayList<Profile> getAllProfiles() {
    return null;
  }

  public Profile getProfile(String username) {
    submittedUsername = username;
    profileSent = dummyProfile;
    return profileSent;
  }

  public void addProfile(Profile profile) { }

  public void save() {

  }


}