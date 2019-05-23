package fsc.service.authorizer;

import fsc.entity.Session;
import fsc.entity.UnAuthorizedSession;
import fsc.service.Authenticator;

public class RejectingAuthorizerStub extends AuthorizerDummy {
  @Override
  public Session authorize(String username, String password)
  {
    return new UnAuthorizedSession();
  }

  public Authenticator authenticate(String username, String password) {
    return null;
  }
}
