package fsc.gateway;

import fsc.entity.AuthorizedSession;
import fsc.entity.Session;

public interface SessionGateway {
  void addSession(AuthorizedSession session);
  Session getSession(String token);
  void save();
}
