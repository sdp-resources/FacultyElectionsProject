package fsc.entity;

public class UnAuthorizedSession implements Session {
  public boolean isAuthorized() {
    return false;
  }
}
