package fsc.mock;
import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;

import java.util.ArrayList;

public class ProfileExistsGatewaySpy implements ProfileGatewayInterface {



  public ArrayList<Profile> getProfiles() {
    return null;
  }

  public Profile getProfileWithUsername(String username) {
    return null;
  }

  public Profile getProfileWithUsername(
        String username, ArrayList<Profile> profiles
  ) {
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
}

