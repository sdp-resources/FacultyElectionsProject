package fsc.mock.gateway.session;

import fsc.entity.Session;
import fsc.gateway.SessionGateway;

public class RejectingSessionGatewayStub extends SessionGatewayDummy {
  @Override
  public Session getSession(String token) throws SessionGateway.NoSessionWithThatTokenException
  {
    throw new SessionGateway.NoSessionWithThatTokenException();
  }
}
