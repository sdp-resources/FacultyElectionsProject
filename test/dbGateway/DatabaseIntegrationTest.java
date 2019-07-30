package dbGateway;

import fsc.entity.PasswordRecord;
import fsc.entity.session.AuthenticatedSession;
import fsc.gateway.SessionGateway;
import fsc.response.Response;
import fsc.service.Authorizer;
import fsc.service.SQLAuthenticator;
import fsc.viewable.ViewableSession;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

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
      PasswordRecord passwordRecord = new SQLAuthenticator(gateway)
                                            .createPasswordRecord("admin",
                                                                  "mypassword",
                                                                  Authorizer.Role.ROLE_ADMIN);
      gateway.addPasswordRecord(passwordRecord);
      gateway.save();
    });
  }

  private String logInAsAdminAndRetrieveToken() {
    return returnWithNewContext(appContext -> {
      Response response = appContext.login("admin", "mypassword");
      ViewableSession session = response.getValues();
      return session.token;
    });
  }

  private void adjustSessionExpiryTimeToTenMinutes(String token) {
    withNewGateway(gateway -> {
      try {
        gateway.begin();
        AuthenticatedSession session = gateway.getSession(token);
        session.setExpirationTime(tenMinutesFromNow());
        gateway.save();
      } catch (SessionGateway.InvalidOrExpiredTokenException e) {
        throw new RuntimeException("failed");
      }
    });
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
    withNewGateway(gateway -> {
      try {
        AuthenticatedSession session = gateway.getSession(token);
        assertDifferenceWithinOneMinute(session.getExpirationTime(), LocalDateTime.now());
      } catch (SessionGateway.InvalidOrExpiredTokenException e) {
        throw new RuntimeException("failed");
      }
    });
  }

  private void assertDifferenceWithinOneMinute(LocalDateTime time1, LocalDateTime time2) {
    assertTrue(Duration.between(time1, time2).abs().getSeconds() <= 60);
  }

  private LocalDateTime tenMinutesFromNow() {
    return LocalDateTime.now().plusMinutes(10);
  }

}