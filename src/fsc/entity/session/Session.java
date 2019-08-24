package fsc.entity.session;

import fsc.service.Authorizer;

public interface Session {
  boolean isAuthenticated();
  boolean isAuthorizedForRole(Authorizer.Role role);
  boolean matchesUser(String username);
  default boolean isAuthorizedAsAdmin() {
    return isAuthorizedForRole(Authorizer.Role.ROLE_ADMIN);
  }
  default boolean isAuthorizedAsUser() {
    return isAuthorizedForRole(Authorizer.Role.ROLE_USER);
  }
}
