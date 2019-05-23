package fsc.mock;

import fsc.service.Authenticator;

public class AcceptingAuthenticatorDummy implements Authenticator {
  public boolean authenticate(String username, String password) {
    return true;
  }
}
