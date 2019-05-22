package fsc.gateway;

import fsc.entity.Session;

public interface SessionGateway {
  void addSession(Session session);
  Session getSession(String token);
  void save();
}
