package fsc.service;

import fsc.MyTime;
import fsc.entity.session.AuthenticatedSession;
import fsc.service.Authorizer.Role;

public class SessionCreator {
  private static final int STANDARD_DURATION_MINS = 30;
  private static final int TOKEN_LENGTH = 24;

  public SessionCreator() { }

  public AuthenticatedSession createSession(Role role, String username) {
    return createSession(role, username, CryptoWrapper.generateToken(TOKEN_LENGTH));
  }

  private AuthenticatedSession createSession(Role role, String username, String token) {
    return createSession(role, username, token, STANDARD_DURATION_MINS);
  }

  private AuthenticatedSession createSession(
        Role role, String username, String token, long minutes
  ) {
    return new AuthenticatedSession(role, username, token, generateExpiryDate(minutes));
  }

  public static MyTime generateExpiryDate(long minutes) {
    return MyTime.fromNow(minutes);
  }

}