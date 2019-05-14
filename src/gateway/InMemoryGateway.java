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

  public Profile getProfileWitheUsername(String username, ArrayList<Profile> profiles) {
    for (Profile currProfile : profiles) {
      if (isCorrectProfile(username, currProfile)) return currProfile;
    }
    return null;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public void clearProfileList() {

  }

  private static boolean isCorrectProfile(String username, Profile currProfile) {
    if (currProfile.username == username) {
      return true;
    }
    return false;
  }

}