package fsc.entity.session;

import fsc.service.Authorizer;

public interface Session {
  boolean isAuthorized();
  boolean isAuthorizedForRole(Authorizer.Role role);
  boolean matchesUser(String username);
}
