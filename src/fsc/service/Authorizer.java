package fsc.service;

import fsc.entity.Authorization;

public interface Authorizer {
  Authorization authorize(String username, String password);
}
