package fsc.mock.gateway.profile;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

public class InvalidProfileGatewaySpy extends ProfileGatewayStub {
  public String submittedUsername;

  @Override
  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    submittedUsername = username;
    throw new ProfileGateway.InvalidProfileUsernameException();
  }
}
