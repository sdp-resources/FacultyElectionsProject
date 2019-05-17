package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;
import java.util.List;

public class NoProfileExistsProfileGatewaySpy implements ProfileGateway {

  public Profile getProfileFromUsername(String username) throws InvalidProfileUsernameException {
    throw new InvalidProfileUsernameException();
  }

  public List<Profile> getAllProfiles() {
    return null;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public boolean isValidDivision(String division) {
    return false;
  }

  public void saveProfile(Profile profile) {

  }
}
