package fsc.service.authorizer;

import fsc.entity.session.Session;
import fsc.entity.session.UnauthenticatedSession;

public class RejectingAuthorizerStub extends AuthorizerDummy {
  @Override
  public Session authorize(String username, String password) {
    return new UnauthenticatedSession();
  }

}
