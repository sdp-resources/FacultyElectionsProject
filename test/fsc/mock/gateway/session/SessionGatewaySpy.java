package fsc.mock.gateway.session;

import fsc.entity.Session;

public class SessionGatewaySpy extends SessionGatewayDummy {
  public Session addedSession = null;
  public boolean saveCalled = false;

  private Session storedSession;

  public SessionGatewaySpy() {}

  public SessionGatewaySpy(Session storedSession)
  {
    this.storedSession = storedSession;
  }

  @Override
  public void addSession(Session session)
  {
    addedSession = session;
  }

  @Override
  public Session getSession(String token)
  {
    return storedSession;
  }

  @Override
  public void save()
  {
    saveCalled = true;
  }
}
