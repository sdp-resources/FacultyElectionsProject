package fsc.service.authorizer;

import fsc.entity.Authorization;
import fsc.entity.AuthorizedAuthorization;

import java.util.Calendar;

public class AcceptingAuthorizerStub extends AuthorizerDummy {
  private String role;
  private String token;

  public AcceptingAuthorizerStub(String role, String token)
  {
    this.role = role;
    this.token = token;
  }

  @Override
  public Authorization authorize(String username, String password)
  {
    return new AuthorizedAuthorization(role, username, token, Calendar.getInstance());
  }
}
