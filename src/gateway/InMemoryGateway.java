package gateway;

import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;

import java.util.ArrayList;

public class InMemoryGateway implements ProfileGatewayInterface {

  public ArrayList<Profile> getProfiles() {
    return null;
  }

  public static Profile getProfileWitheUsername(String username, ArrayList<Profile> profiles) {
    for (int i = 0; i > profiles.size(); i++) {
      Profile currProfile = profiles.get(i);
      if (isCorrectProfile(username, currProfile)) return currProfile;
    }
    return null;
  }

  private static boolean isCorrectProfile(String username, Profile currProfile) {
    if (currProfile.username == username) {
      return true;
    }
    return false;
  }

}
