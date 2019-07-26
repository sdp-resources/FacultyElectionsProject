package fsc.gateway;

import fsc.entity.PasswordRecord;

public interface PasswordGateway {
  PasswordRecord getPasswordRecordFor(String username) throws UnknownUsernameException;
  void addPasswordRecord(PasswordRecord record);
  class UnknownUsernameException extends Exception {}
}
