package fsc.mock.gateway.session;

import fsc.entity.session.AuthenticatedSession;

public class SessionGatewaySpy extends SessionGatewayStub {
  public AuthenticatedSession addedSession = null;
  public boolean hasSaved = false;

  private AuthenticatedSession storedSession;
  public AuthenticatedSession renewedSession;

  public SessionGatewaySpy() {}

  public SessionGatewaySpy(AuthenticatedSession storedSession) {
    this.storedSession = storedSession;
  }

  @Override
  public void addSession(AuthenticatedSession session) {
    addedSession = session;
  }

  @Override
  public AuthenticatedSession getSession(String token) throws InvalidOrExpiredTokenException {
    if (storedSession != null && storedSession.getToken().equals(token)) {
      return storedSession;
    }
    throw new InvalidOrExpiredTokenException();
  }

  @Override
  public void renew(AuthenticatedSession session) {
    renewedSession = session;
  }
}
