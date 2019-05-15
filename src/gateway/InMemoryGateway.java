package gateway;

import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;

import java.util.ArrayList;

public class InMemoryGateway implements ProfileGatewayInterface {

  public static ArrayList<Profile> profileList;

  public InMemoryGateway() {
    this.profileList = null;
  }

  public ArrayList<Profile> getProfiles() {
    return profileList;
  }

  public void addToProfiles(Profile newProfile) {
    profileList.add(newProfile);
  }

  public Profile getProfileWithUsername(String username) {
    for (Profile currProfile : profileList) {
      if (isCorrectProfile(username, currProfile)) return currProfile;
    }
    return null;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public void clearProfileList() {

  }

  public Profile getProfile(String userName) {
    return null;
  }

  private static boolean isCorrectProfile(String username, Profile currProfile) {
    if (currProfile.username == username) {
      return true;
    }
    return false;
  }

}
