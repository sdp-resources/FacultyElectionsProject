package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.mock.AcceptingAuthenticatorDummy;
import fsc.mock.gateway.session.SessionGatewayDummy;
import fsc.mock.gateway.session.SessionGatewaySpy;
import fsc.request.LoginRequest;
import fsc.response.*;
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

    Response response = loginInteractor.execute(request);

    assertEquals(ResponseFactory.notAuthorized(), response);
  }

  @Test
  public void authorizationAcceptedGivesTokenAndRoles() {
    String username = "admin";
    String password = "1234";
    LoginRequest request = new LoginRequest(username, password);

    Authorizer.Role expectedRole = Authorizer.Role.ROLE_ADMIN;
    String expectedToken = "jlksdfgj";

    Authenticator authenticator = new AcceptingAuthenticatorDummy();
    Authorizer authorizer = new AcceptingAuthorizerStub(expectedRole, expectedToken);
    SessionGatewaySpy sessionGateway = new SessionGatewaySpy();
    LoginInteractor loginInteractor = new LoginInteractor(sessionGateway, authorizer,
                                                          authenticator);

    LoginResponse response = (LoginResponse) loginInteractor.execute(request);

    assertEquals(expectedRole.toString(), response.role);
    assertEquals(expectedToken, response.token);
    assertEquals(sessionGateway.addedSession.getToken(), response.token);
    assertEquals(expectedRole, sessionGateway.addedSession.getRole());
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
