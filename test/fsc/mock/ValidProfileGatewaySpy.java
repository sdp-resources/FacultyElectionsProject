package fsc.mock;

import fsc.entity.Profile;

public class ValidProfileGatewaySpy extends ProfileGatewayStub {
  public String usernameReceived;
  public Profile profileSent;

  public ValidProfileGatewaySpy(Profile... profiles) {
    super(profiles);
  }

  public Profile getProfile(String username) throws InvalidProfileUsernameException {
    usernameReceived = username;
    profileSent = super.getProfile(username);
    return profileSent;
  }
}
