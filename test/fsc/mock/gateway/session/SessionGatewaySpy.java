package fsc.mock.gateway.session;

import fsc.entity.AuthorizedSession;
import fsc.entity.Session;

public class SessionGatewaySpy extends SessionGatewayDummy {
  public AuthorizedSession addedSession = null;
  public boolean saveCalled = false;

  private Session storedSession;

  public SessionGatewaySpy() {}

  public SessionGatewaySpy(Session storedSession)
  {
    this.storedSession = storedSession;
  }

  @Override
  public void addSession(AuthorizedSession session)
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
