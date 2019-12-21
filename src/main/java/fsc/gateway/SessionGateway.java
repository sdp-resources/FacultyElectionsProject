package fsc.gateway;

import fsc.entity.session.AuthenticatedSession;

public interface SessionGateway {
  void addSession(AuthenticatedSession session);
  AuthenticatedSession getSession(String token) throws InvalidOrExpiredTokenException;
  void renew(AuthenticatedSession session);

  default AuthenticatedSession getAndRenewIfActive(String token)
        throws InvalidOrExpiredTokenException {
    AuthenticatedSession session = getSession(token);
    if (session.hasExpired()) throw new InvalidOrExpiredTokenException();
    renew(session);

    return session;
  }

  class InvalidOrExpiredTokenException extends Exception {}
}
