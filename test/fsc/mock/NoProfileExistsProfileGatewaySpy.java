package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.List;

public class NoProfileExistsProfileGatewaySpy implements ProfileGateway {

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    throw new InvalidProfileUsernameException();
  }

  public List<Profile> getAllProfiles() {
    return null;
  }

  public void addProfile(Profile profile) { }

  public void save() {

  }

  public boolean isValidDivision(String division) {
    return false;
  }

}
