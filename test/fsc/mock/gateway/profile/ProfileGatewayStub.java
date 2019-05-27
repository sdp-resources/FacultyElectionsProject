package fsc.mock.gateway.profile;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.Arrays;
import java.util.List;

public class ProfileGatewayStub implements ProfileGateway {
  private final List<Profile> profiles;
  public boolean getAllProfilesWasCalled = false;

  public ProfileGatewayStub(Profile... profiles) {
    this.profiles = Arrays.asList(profiles);
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
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
}
