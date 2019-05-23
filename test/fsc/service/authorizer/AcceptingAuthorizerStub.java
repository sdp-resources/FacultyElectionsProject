package fsc.service.authorizer;

import fsc.entity.*;

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
  public Session authorize(String username, String password)
  {
    return new AuthorizedSession(role, username, token, Calendar.getInstance());
  }
}
