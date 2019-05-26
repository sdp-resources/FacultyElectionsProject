package fsc.service.authorizer;

import fsc.entity.session.Session;
import fsc.service.Authenticator;
import fsc.service.Authorizer;

public class AuthorizerDummy implements Authorizer {

  public Session authorize(String username, String password)
  {
    return null;
  }

  public Authenticator authenticator() {
    return null;
  }
}
