package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.Gateway;

import java.util.List;

public class NoProfileGatewaySpy implements Gateway {

  public List<Profile> getAllProfiles() {
    return null;
  }
}
