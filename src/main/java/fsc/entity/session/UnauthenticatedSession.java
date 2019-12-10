package fsc.entity.session;

import fsc.service.Authorizer;

public class UnauthenticatedSession implements Session {
  public boolean isAuthenticated() {
    return false;
  }

  public boolean isAuthorizedForRole(Authorizer.Role role) {
    return false;
  }

  public boolean matchesUser(String username) {
    return false;
  }
}
