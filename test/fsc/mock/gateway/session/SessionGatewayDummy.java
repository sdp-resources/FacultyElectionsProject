package fsc.mock.gateway.session;

import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;
import fsc.gateway.SessionGateway;

public class SessionGatewayDummy implements SessionGateway {
  public void addSession(AuthenticatedSession session) {}

  public Session getSession(String token) throws SessionGateway.NoSessionWithThatTokenException {
    return null;
  }

  public void save() {}
}
