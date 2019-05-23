package fsc.service;

public interface Authenticator {
  boolean authenticate(String username, String password);
}
