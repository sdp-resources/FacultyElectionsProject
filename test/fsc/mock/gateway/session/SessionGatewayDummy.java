package fsc.mock.gateway.session;

import fsc.entity.Session;
import fsc.gateway.SessionGateway;

public class SessionGatewayDummy implements SessionGateway {
  public void addSession(Session session) {

  }

  public Session getSession(String token) {
    return null;
  }

  public void save() {

  }
}
