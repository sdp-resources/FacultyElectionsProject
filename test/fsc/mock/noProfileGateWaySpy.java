package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.List;

public class noProfileGateWaySpy implements ProfileGateway {

  public Profile getProfile(String username) {
    return null;
  }

  public List<Profile> getAllProfiles() {
    return null;
  }

  public void addProfile(Profile profile) { }

  public void save() {

  }

  public void clearProfileList() {

  }

  public boolean isValidDivision(String division) {
    return false;
  }

}
