package dbGateway;

import fsc.entity.PasswordRecord;
import fsc.gateway.PasswordGateway;
import fsc.service.Authorizer;
import fsc.service.Credentials;
import fsc.service.SQLAuthenticator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DatabasePasswordsTest extends BasicDatabaseTest {

  private PasswordRecord record;

  @Test
  public void canAddPasswordRecord() throws PasswordGateway.UnknownUsernameException {
    saveARecord();
    PasswordRecord record2 = anotherGateway.getPasswordRecordFor("admin");
    assertEquals(record, record2);
  }

  private void saveARecord() {
    Credentials credentials = new Credentials("admin", "aPassword");
    record = new PasswordRecord(credentials.getUsername(),
                                SQLAuthenticator.hashPassword(credentials),
                                Authorizer.Role.ROLE_ADMIN);
    gateway.addPasswordRecord(record);
    gateway.commit();
  }

}