package fsc.mock.gateway.session;

import fsc.entity.session.AuthenticatedSession;
import fsc.gateway.SessionGateway;

public class SessionGatewayStub implements SessionGateway {

  public String token;

  public void addSession(AuthenticatedSession session) {}

  public AuthenticatedSession getSession(String token) throws InvalidOrExpiredTokenException {
    return null;
  }

  public void renew(AuthenticatedSession session) {}
}
