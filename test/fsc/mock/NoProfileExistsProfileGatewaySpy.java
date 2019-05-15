package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;
import java.util.List;

public class NoProfileExistsProfileGatewaySpy implements ProfileGateway {

  public ArrayList<Profile> getProfiles() {
    return null;
  }

  public Profile getProfileFromUsername(String username) {
    return null;
  }

  public List<Profile> getAllProfiles() {
    return null;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }
}
