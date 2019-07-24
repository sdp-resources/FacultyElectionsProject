package fsc.entity.session;

import fsc.service.Authorizer;

public class UnAuthorizedSession implements Session {
  public boolean isAuthorized() {
    return false;
  }

  public boolean isAuthorizedForRole(Authorizer.Role role) {
    return false;
  }
}
