package fsc.entity;

public class NotAuthorizedAuthorization implements Authorization
{
  public boolean isAuthorized() { return false; }
}
