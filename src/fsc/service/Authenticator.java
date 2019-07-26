package fsc.service;

import fsc.entity.PasswordRecord;
import fsc.entity.session.Session;

public interface Authenticator {
  Session authenticateWithCredentials(Credentials credentials);
  // TODO: Authenticator has no business doing this
  PasswordRecord createPasswordRecord(String username, String password, Authorizer.Role role);
}
