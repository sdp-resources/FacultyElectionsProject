package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.Gateway;

import java.util.List;

public class GatewayDummy implements Gateway {
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
