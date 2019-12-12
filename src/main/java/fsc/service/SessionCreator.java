package fsc.service;

import fsc.entity.session.AuthenticatedSession;

import java.time.LocalDateTime;

public class SessionCreator {
  public static final int SESSION_DURATION_MINUTES = 30;
  public static final int TOKEN_LENGTH = 24;

  public SessionCreator() { }

  public AuthenticatedSession createSession(Authorizer.Role role, String username) {
    return new AuthenticatedSession(role,
                                    username,
                                    CryptoWrapper.generateToken(TOKEN_LENGTH),
                                    generateExpiryDate());
  }

  public LocalDateTime generateExpiryDate() {
    return LocalDateTime.now().plusMinutes(SESSION_DURATION_MINUTES);
  }

}