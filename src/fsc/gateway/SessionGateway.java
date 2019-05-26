package fsc.gateway;

import fsc.entity.session.AuthorizedSession;
import fsc.entity.session.Session;

public interface SessionGateway {
  void addSession(AuthorizedSession session);
  Session getSession(String token) throws NoSessionWithThatTokenException;
  void save();
  class NoSessionWithThatTokenException extends Exception {}
}
