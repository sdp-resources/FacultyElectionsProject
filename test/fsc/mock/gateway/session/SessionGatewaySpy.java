package fsc.mock.gateway.session;

import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.Session;

public class SessionGatewaySpy extends SessionGatewayStub {
  public AuthenticatedSession addedSession = null;
  public boolean saveCalled = false;

  private Session storedSession;

  public SessionGatewaySpy() {}

  public SessionGatewaySpy(Session storedSession) {
    this.storedSession = storedSession;
  }

  @Override
  public void addSession(AuthenticatedSession session) {
    addedSession = session;
  }

  @Override
  public Session getSession(String token) {
    return storedSession;
  }

  @Override
  public void save() {
    saveCalled = true;
  }
}
