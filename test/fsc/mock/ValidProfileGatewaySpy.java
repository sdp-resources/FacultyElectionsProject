package fsc.mock;

import fsc.entity.Profile;

import java.util.List;

public class ValidProfileGatewaySpy extends ProfileGatewayStub {
  public String usernameReceived;
  public Profile profileSent;

  public Profile getProfileFromUsername(String username) throws InvalidProfileUsernameException {
    usernameReceived = username;
    profileSent = super.getProfileFromUsername(username);
    return profileSent;
  }
}
