package fsc.service;

import fsc.entity.PasswordRecord;
import fsc.gateway.PasswordGateway;

import java.util.ArrayList;
import java.util.Collection;

public class SimplePasswordGatewaySpy implements PasswordGateway {
  public String requestedUsername;
  Collection<PasswordRecord> records = new ArrayList<>();
  public boolean hasSaved = false;

  public PasswordRecord getPasswordRecordFor(String username) throws UnknownUsernameException {
    requestedUsername = username;
    for (PasswordRecord record : records) {
      if (record.getUsername().equals(username)) { return record; }
    }
    throw new UnknownUsernameException();
  }

  public boolean hasPasswordRecordFor(String username) {
    for (PasswordRecord record : records) {
      if (record.getUsername().equals(username)) { return true; }
    }

    return false;
  }

  public void addPasswordRecord(PasswordRecord record) {
    records.add(record);
  }

  public void save() {
    hasSaved = true;
  }
}
