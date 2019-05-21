package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileGatewayStub implements ProfileGateway {
  public Profile profile;
  private List<Profile> profiles;

  public ProfileGatewayStub(Profile... profiles) {
    this.profiles = Arrays.asList(profiles);
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    return profiles.get(0);
  }

  public List<Profile> getAllProfiles() {
    return profiles;
  }

  public void addProfile(Profile profile) { }

  public boolean isValidDivision(String division) {
    return false;
  }

  public void saveProfile(fsc.entity.Profile profile) {

  }

  public Profile getAProfile() {
    return profiles.get(0);
  }
}
