package fsc.gateway;

import fsc.entity.session.AuthenticatedSession;

public interface SessionGateway {
  void addSession(AuthenticatedSession session);
  AuthenticatedSession getSession(String token) throws InvalidOrExpiredTokenException;
  default AuthenticatedSession getActiveSession(String token)
        throws InvalidOrExpiredTokenException {
    AuthenticatedSession session = getSession(token);
    if (session.hasExpired()) {
      throw new InvalidOrExpiredTokenException();
    }

    return session;
  }
  void save();
  class InvalidOrExpiredTokenException extends Exception {}
}
