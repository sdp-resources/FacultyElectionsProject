package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;

public class InvalidProfileGatewaySpy extends ProfileGatewayStub {
  public String usernameReceived;

  @Override
  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    usernameReceived = username;
    throw new ProfileGateway.InvalidProfileUsernameException();
  }
}
