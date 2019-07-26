package fsc.interactor;

import fsc.entity.PasswordRecord;
import fsc.gateway.PasswordGateway;
import fsc.mock.gateway.session.SessionGatewaySpy;
import fsc.request.AddPasswordRecordRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddPasswordRecordInteractionTest {
  private LoginInteractor interactor;
  private SessionGatewaySpy sessionGateway;
  private SimplePasswordGatewaySpy passwordGateway = new SimplePasswordGatewaySpy();
  private AddPasswordRecordRequest request;

  @Test
  public void whenAddingRecordForExistingUsername_givesErrorResponse() {
    String username = "admin";
    String password = "1234";
    Authorizer.Role role = Authorizer.Role.ROLE_ADMIN;
    request = new AddPasswordRecordRequest(username, password, role.toString());

    SQLAuthenticator authenticator = new SQLAuthenticator(passwordGateway);
    PasswordRecord record = authenticator.createPasswordRecord(username, password, role);
    passwordGateway.addPasswordRecord(record);
    interactor = new LoginInteractor(null, authenticator, passwordGateway);

    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.resourceExists(), response);
  }

  @Test
  public void whenAddingRecordForNonExistingUsername_addsRecord()
        throws PasswordGateway.UnknownUsernameException {
    String username = "admin";
    String password = "1234";
    Authorizer.Role role = Authorizer.Role.ROLE_ADMIN;
    request = new AddPasswordRecordRequest(username, password, role.toString());

    SQLAuthenticator authenticator = new SQLAuthenticator(passwordGateway);
    interactor = new LoginInteractor(null, authenticator, passwordGateway);

    Response response = interactor.handle(request);

    assertEquals(ResponseFactory.success(), response);
    assertTrue(passwordGateway.hasSaved);
    PasswordRecord retrievedRecord = passwordGateway.getPasswordRecordFor(username);
    assertEquals(role, retrievedRecord.getRole());

  }

  //  @Test
//  public void authorizationAccepted_givesTokenAndRole() {
//    String username = "admin";
//    String password = "1234";
//    LoginRequest request = new LoginRequest(username, password);
//
//    Authorizer.Role expectedRole = Authorizer.Role.ROLE_ADMIN;
//
//    AcceptingAuthenticatorSpy authenticator = new AcceptingAuthenticatorSpy();
//    sessionGateway = new SessionGatewaySpy();
//    interactor = new LoginInteractor(sessionGateway, authenticator, null);
//
//    Response response = interactor.handle(request);
//
//    ViewableSession viewableSession = response.getValues();
//
//    assertEquals(expectedRole.toString(), viewableSession.role);
//    assertEquals(authenticator.token, viewableSession.token);
//    assertEquals(sessionGateway.addedSession.getToken(), viewableSession.token);
//    assertEquals(expectedRole, sessionGateway.addedSession.getRole());
//    assertTrue(sessionGateway.saveCalled);
//  }
}
