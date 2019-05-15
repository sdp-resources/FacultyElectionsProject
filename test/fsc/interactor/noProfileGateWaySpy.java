package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;

import java.util.ArrayList;

public class noProfileGateWaySpy implements ProfileGatewayInterface {

  public ArrayList<Profile> getProfiles() {
    return null;
  }

  public Profile getProfileWithUsername(String username) {
    return null;
  }

  public Profile getProfileWitheUsername(
        String username, ArrayList<Profile> profiles
  ) {
    return null;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public void clearProfileList() {

  }

  public Boolean isValidDivision(String division) {
    return null;
  }

  public Profile getProfile(String userName) {
    return null;
  }
}
