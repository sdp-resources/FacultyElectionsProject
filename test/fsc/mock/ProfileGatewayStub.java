package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;
import java.util.List;

public class ProfileGatewayStub implements ProfileGateway {
  public Profile Profile = new Profile("Adam Jones", "jonesa", "SCI", "Tenured");

  public Profile getProfileFromUsername(String username) throws InvalidProfileUsernameException {
    return Profile;
  }

  public List<Profile> getAllProfiles() {
    List<Profile> profiles = new ArrayList<Profile>();
    profiles.add(Profile);
    return profiles;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public boolean isValidDivision(String division) {
    return false;
  }

  public void updateProfile(fsc.entity.Profile profile) {

  }
}
