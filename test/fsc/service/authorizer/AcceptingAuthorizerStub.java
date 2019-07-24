package fsc.service.authorizer;

import fsc.entity.session.AuthorizedSession;
import fsc.entity.session.Session;

import java.time.LocalDateTime;

public class AcceptingAuthorizerStub extends AuthorizerDummy {
  private final Role role;
  private final String token;

  public AcceptingAuthorizerStub(Role role, String token) {
    this.role = role;
    this.token = token;
  }

  @Override
  public Session authorize(String username, String password) {
    return new AuthorizedSession(role, username, token, LocalDateTime.now());
  }
}
