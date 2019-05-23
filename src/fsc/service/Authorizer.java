package fsc.service;

import fsc.entity.Session;

public interface Authorizer{
  Session authorize(String username, String password);
  enum Role { ROLE_USER, ROLE_ADMIN }
  Authenticator authenticator();

}
