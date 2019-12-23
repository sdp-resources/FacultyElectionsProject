package dbGateway;

import fsc.MyTime;
import fsc.entity.PasswordRecord;
import fsc.entity.session.AuthenticatedSession;
import fsc.gateway.SessionGateway;
import fsc.response.Response;
import fsc.service.Authorizer;
import fsc.viewable.ViewableSession;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DatabaseIntegrationTest extends BasicDatabaseTest {

  @Test
  public void fullRoundTripWithLoggingAndAddingContractType() {
    setUpAdminAccount();
    String token = logInAsAdminAndRetrieveToken();
    adjustSessionExpiryTimeToTenMinutes(token);
    submitCorrectNewContractTypeRequest(token);
    examineSessionExpiryTimeIsAtThirtyMinutes(token);
    adjustSessionExpiryTimeToTenMinutes(token);
    submitIncorrectNewContractTypeRequest(token);
    examineSessionExpiryTimeIsAtThirtyMinutes(token);
  }

  private void setUpAdminAccount() {
    withNewGateway(gateway -> {
      gateway.begin();
      PasswordRecord passwordRecord = PasswordRecord.create("admin", "mypassword",
                                                            Authorizer.Role.ROLE_ADMIN);
      gateway.addPasswordRecord(passwordRecord);
      gateway.save();
    });
  }

  private String logInAsAdminAndRetrieveToken() {
    return returnWithNewContext(appContext -> {
      Response<ViewableSession> response = appContext.login("admin", "mypassword");
      ViewableSession session = response.getValues();
      return session.token;
    });
  }

  private void adjustSessionExpiryTimeToTenMinutes(String token) {
    try {
      SessionGateway sessionGateway = new RedisStore("localhost");
      AuthenticatedSession session = sessionGateway.getSession(token);
      sessionGateway.renew(session);
    } catch (
            SessionGateway.InvalidOrExpiredTokenException e) {
      throw new RuntimeException("failed");
    }
  }

  private void submitCorrectNewContractTypeRequest(String token) {
    assertTrue(submitRequest(token).isSuccessful());
  }

  private void submitIncorrectNewContractTypeRequest(String token) {
    assertFalse(submitRequest(token).isSuccessful());
  }

  private Response submitRequest(String token) {
    return returnWithNewContext(
          appContext -> appContext.addContractType("aType", token));
  }

  private void examineSessionExpiryTimeIsAtThirtyMinutes(String token) {
    try {
      SessionGateway sessionGateway = new RedisStore("localhost");
      AuthenticatedSession session = sessionGateway.getSession(token);
      assertDifferenceWithinOneMinute(session.getExpirationTime(), MyTime.fromNow(30));
    } catch (SessionGateway.InvalidOrExpiredTokenException e) {
      throw new RuntimeException("failed");
    }
  }

  private void assertDifferenceWithinOneMinute(MyTime time1, MyTime time2) {
    assertTrue(MyTime.withinOneMinute(time1, time2));
  }

}