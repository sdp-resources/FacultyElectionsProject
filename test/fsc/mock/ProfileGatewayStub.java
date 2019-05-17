package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;
import java.util.List;

public class ProfileGatewayStub implements ProfileGateway {
  public Profile profile1 = new Profile("Adam Jones", "jonesa", "SCI", "Tenured");
  public Profile profile2 = new Profile("Boogie Arrowood", "arrowoodb", "ART", "Tenured");
  public Profile profile3 = new Profile("Gabe Beck", "beckg", "ART", "Tenured");

  public Profile getProfileFromUsername(String username) throws InvalidProfileUsernameException {
    return profile1;
  }

  public List<Profile> getAllProfiles() {
    List<Profile> profiles = new ArrayList<Profile>();
    profiles.add(profile1);
    profiles.add(profile2);
    profiles.add(profile3);
    return profiles;
  }

  public Profile addProfile(Profile profile) {
    return null;
  }

  public boolean isValidDivision(String division) {
    return false;
  }

  public void saveProfile(fsc.entity.Profile profile) {

  }
}
