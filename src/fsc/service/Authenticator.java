package fsc.service;

import fsc.entity.session.Session;

public interface Authenticator {
  Session authenticateWithCredentials(Credentials credentials);
}
