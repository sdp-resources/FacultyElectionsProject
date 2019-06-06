package fsc.mock.gateway.profile;

import fsc.entity.Profile;
import fsc.entity.query.Query;
import fsc.gateway.ProfileGateway;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileGatewayStub implements ProfileGateway {
  private final List<Profile> profiles;
  public boolean getAllProfilesWasCalled = false;

  public ProfileGatewayStub(Profile... profiles) {
    this.profiles = Arrays.asList(profiles);
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    for (Profile profile : profiles) {
      if (profile.getUsername().equals(username)) { return profile; }
    }

    return profiles.get(0);
  }

  public void addProfile(Profile profile) { }

  public boolean hasProfile(String username) {
    return false;
  }

  public void save() {}

  public Profile getAProfile() {
    return profiles.get(0);
  }

  public List<Profile> getAllProfiles() {
    getAllProfilesWasCalled = true;
    return profiles;
  }

  public List<Profile> getProfilesMatching(Query query) {
    return profiles.stream().filter(query::isProfileValid).collect(Collectors.toList());
  }
}
