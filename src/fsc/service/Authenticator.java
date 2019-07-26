package fsc.service;

import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;

import java.time.LocalDateTime;

public interface Authenticator {

  Session authenticateWithCredentials(Credentials credentials);
}
