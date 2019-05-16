package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.List;

public class GetProfileFromUsernameProfileGatewayStub implements ProfileGateway {
  public Profile getProfileFromUsername(String username) throws InvalidProfileUsernameException {
    return new Profile("Adam Jones", "jonesa", "SCI", "Tenured");
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
}
