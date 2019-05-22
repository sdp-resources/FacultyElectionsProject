package fsc.entity;

public class NotAuthorized implements Authorization
{
  public boolean isAuthorized() { return false; }
}
