package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.Gateway;

import java.util.List;

public class GatewayDummy implements Gateway {
  public List<Profile> getAllProfiles() {
    return null;
  }
}
