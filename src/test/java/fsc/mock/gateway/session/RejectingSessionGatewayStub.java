package fsc.mock.gateway.session;

import fsc.entity.session.AuthenticatedSession;

public class RejectingSessionGatewayStub extends SessionGatewayStub {
  @Override
  public AuthenticatedSession getSession(String token) throws InvalidOrExpiredTokenException {
    throw new InvalidOrExpiredTokenException();
  }
}
