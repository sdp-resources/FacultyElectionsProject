package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.Gateway;

import java.util.List;

public class NoBallotGatewaySpy implements Gateway {
  public List<Profile> getAllProfiles() {
    return null;
  }
}
