package fsc.service.authorizer;

import fsc.entity.Session;
import fsc.service.Authorizer;

public class AuthorizerDummy implements Authorizer {

  public Session authorize(String username, String password)
  {
    return null;
  }
}
