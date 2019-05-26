package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.mock.AcceptingAuthenticatorDummy;
import fsc.mock.gateway.session.SessionGatewayDummy;
import fsc.mock.gateway.session.SessionGatewaySpy;
import fsc.request.LoginRequest;
import fsc.response.ErrorResponse;
import fsc.response.LoginResponse;
import fsc.service.Authenticator;
import fsc.service.Authorizer;
import fsc.service.authorizer.AcceptingAuthorizerStub;
import fsc.service.authorizer.AuthorizerDummy;
import fsc.service.authorizer.RejectingAuthorizerStub;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginInteractorTest {
  @Test
  public void authorizationRejectedGivesErrorResponse() {
    String username = "admin";
    String password = "1234";
    LoginRequest request = new LoginRequest(username, password);

    Authenticator authenticator = new AcceptingAuthenticatorDummy();
    Authorizer authorizer = new RejectingAuthorizerStub();
    SessionGateway sessionGateway = new SessionGatewayDummy();
    LoginInteractor loginInteractor = new LoginInteractor(sessionGateway, authorizer,
                                                          authenticator);

    ErrorResponse response = (ErrorResponse) loginInteractor.execute(request);

    assertEquals(ErrorResponse.notAuthorized(), response);
  }

  @Test
  public void authorizationAcceptedGivesTokenAndRoles() {
    String username = "admin";
    String password = "1234";
    LoginRequest request = new LoginRequest(username, password);

    String expectedRole = "Admin";
    String expectedToken = "jlksdfgj";

    Authenticator authenticator = new AcceptingAuthenticatorDummy();
    Authorizer authorizer = new AcceptingAuthorizerStub(expectedRole, expectedToken);
    SessionGatewaySpy sessionGateway = new SessionGatewaySpy();
    LoginInteractor loginInteractor = new LoginInteractor(sessionGateway, authorizer,
                                                          authenticator);

    LoginResponse response = (LoginResponse) loginInteractor.execute(request);

    assertEquals(expectedRole, response.role);
    assertEquals(expectedToken, response.token);
    assertEquals(sessionGateway.addedSession.getToken(), response.token);
    assertEquals(sessionGateway.addedSession.getRole(), response.role);
    assertTrue(sessionGateway.saveCalled);
  }

  @Ignore
  @Test
  public void authenticatorAccepted() {
    String username = "admin";
    String password = "1234";
    LoginRequest request = new LoginRequest(username, password);

    Authenticator authenticator = new AcceptingAuthenticatorDummy();
    Authorizer authorizer = new AuthorizerDummy();
    SessionGateway sessionGateway = new SessionGatewayDummy();
    LoginInteractor loginInteractor = new LoginInteractor(sessionGateway, authorizer,
                                                          authenticator);

    LoginResponse response = (LoginResponse) loginInteractor.execute(request);
  }
}
