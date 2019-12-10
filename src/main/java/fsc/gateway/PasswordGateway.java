package fsc.gateway;

import fsc.entity.PasswordRecord;

public interface PasswordGateway {
  PasswordRecord getPasswordRecordFor(String username) throws UnknownUsernameException;
  boolean hasPasswordRecordFor(String username);
  void addPasswordRecord(PasswordRecord record);
  void save();
  class UnknownUsernameException extends Exception {}
}
