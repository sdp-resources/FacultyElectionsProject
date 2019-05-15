package gateway;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;

public class InMemoryGateway implements ProfileGateway {

  public static ArrayList<Profile> profileList;

  public InMemoryGateway() {
    this.profileList = null;
  }

  public ArrayList<Profile> getAllProfiles() {
    return profileList;
  }

  public void addToProfiles(Profile newProfile) {
    profileList.add(newProfile);
  }

  public Profile getProfileFromUsername(String username) throws Exception {
    for (Profile currProfile : profileList) {
      if (isCorrectProfile(username, currProfile)) return currProfile;
    }
    throw new Exception("No Profile With that Username");
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public Boolean isValidDivision(String division) {
    return false;
  }

  private static boolean isCorrectProfile(String username, Profile currProfile) {
    return (currProfile.username.equals(username));

  }

}
