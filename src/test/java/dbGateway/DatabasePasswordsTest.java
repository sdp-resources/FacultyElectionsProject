package dbGateway;

import fsc.entity.PasswordRecord;
import fsc.gateway.PasswordGateway;
import fsc.service.Authorizer;
import fsc.service.Credentials;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DatabasePasswordsTest extends BasicDatabaseTest {

  private PasswordRecord record;

  @Test
  public void canAddPasswordRecord() throws PasswordGateway.UnknownUsernameException {
    saveARecord();
    PasswordRecord record2 = anotherGateway.getPasswordRecordFor("admin");
    assertEquals(record, record2);
    assertEquals(true, record2.matchesPassword("aPassword"));
  }

  private void saveARecord() {
    Credentials credentials = new Credentials("admin", "aPassword");
    record = PasswordRecord.create(credentials.getUsername(),
                                   credentials.getPassword(),
                                   Authorizer.Role.ROLE_ADMIN);
    gateway.addPasswordRecord(record);
    gateway.commit();
  }

}