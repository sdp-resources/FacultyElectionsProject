package fsc.gateway;

import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;

public interface SessionGateway {
  void addSession(AuthenticatedSession session);
  Session getSession(String token) throws NoSessionWithThatTokenException;
  void save();
  class NoSessionWithThatTokenException extends Exception {}
}
