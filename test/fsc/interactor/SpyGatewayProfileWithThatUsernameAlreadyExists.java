package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;

import java.util.ArrayList;

public class SpyGatewayProfileWithThatUsernameAlreadyExists implements ProfileGatewayInterface {
  public static ArrayList<Profile> profileList;
  public static String submittedUsername;
  public static Profile dummyProfile = new Profile("a","b","c","d");

  public SpyGatewayProfileWithThatUsernameAlreadyExists(){
    this.profileList = null;
  }


  public ArrayList<Profile> getProfiles() {
    return null;
  }

  public Profile getProfileWithUsername(String username) {
    submittedUsername = username;
    return dummyProfile;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public void clearProfileList() {

  }
}