package fsc.service.authorizer;

import fsc.entity.Authorization;
import fsc.service.Authorizer;

public class AuthorizerDummy implements Authorizer {
  public Authorization authorize(String username, String password)
  {
    return null;
  }
}
