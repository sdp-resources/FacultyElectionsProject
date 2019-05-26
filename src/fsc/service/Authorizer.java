package fsc.service;

import fsc.entity.session.Session;

public interface Authorizer{
  Session authorize(String username, String password);
  enum Role { ROLE_USER, ROLE_ADMIN }

}
