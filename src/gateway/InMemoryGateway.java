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

  public Profile getProfileFromUsername(String username) {
    for (Profile currProfile : profileList) {
      if (isCorrectProfile(username, currProfile)) return currProfile;
    }
    throw new RuntimeException("No Profile With that Username");
  }

  public void addProfile(Profile profile) {
    profileList.add(profile);
  }

  public boolean isValidDivision(String division) {
    return false;
  }

  public void saveProfile(Profile profile) {

  }

  private static boolean isCorrectProfile(String username, Profile currProfile) {
    return (currProfile.username.equals(username));

  }

}
