package fsc.service.authorizer;

import fsc.entity.Authorization;
import fsc.entity.NotAuthorized;

public class RejectingAuthorizerStub extends AuthorizerDummy {
  @Override
  public Authorization authorize(String username, String password)
  {
    return new NotAuthorized();
  }
}
