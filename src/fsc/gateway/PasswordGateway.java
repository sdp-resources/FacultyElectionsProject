package fsc.gateway;

public interface PasswordGateway {
  boolean checkCredentials(String username, String password);
  void updateCredentials(String username, String password);
}
