package fsc.mock;

import fsc.entity.Profile;

import java.util.List;

public class AllProfilesGatewaySpy extends ProfileGatewayStub {
  public boolean getAllProfilesWasCalled = false;

  public AllProfilesGatewaySpy(Profile... profiles) {
    super(profiles);
  }

  public List<Profile> getAllProfiles() {
    getAllProfilesWasCalled = true;
    return super.getAllProfiles();
  }
}
