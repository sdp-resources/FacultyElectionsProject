package fsc.entity.session;

public class UnAuthorizedSession implements Session {
  public boolean isAuthorized() {
    return false;
  }
}
