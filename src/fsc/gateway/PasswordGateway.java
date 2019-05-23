package fsc.gateway;

public interface PasswordGateway {
  boolean checkCredentials();
  void updateCredentials();
}
