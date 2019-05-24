package fsc.service.authorizer;

import fsc.entity.Session;
import fsc.entity.UnAuthorizedSession;

public class RejectingAuthorizerStub extends AuthorizerDummy {
  @Override
  public Session authorize(String username, String password)
  {
    return new UnAuthorizedSession();
  }

}
