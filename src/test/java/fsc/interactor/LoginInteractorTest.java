package fsc.interactor;

import fsc.mock.AcceptingAuthenticatorSpy;
import fsc.mock.RejectingAuthenticatorSpy;
import fsc.mock.gateway.session.SessionGatewaySpy;
import fsc.request.LoginRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.Authenticator;
import fsc.service.Authorizer;
import fsc.viewable.ViewableSession;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginInteractorTest {

  private LoginInteractor loginInteractor;
  private SessionGatewaySpy sessionGateway;

  @Test
  public void authorizationRejected_givesErrorResponse() {
    String username = "admin";
    String password = "1234";
    LoginRequest request = new LoginRequest(username, password);

    Authenticator authenticator = new RejectingAuthenticatorSpy();
    sessionGateway = new SessionGatewaySpy();
    loginInteractor = new LoginInteractor(sessionGateway, authenticator, null);

    Response response = loginInteractor.handle(request);

    assertEquals(ResponseFactory.invalidCredentials(), response);
  }

  @Test
  public void authorizationAccepted_givesTokenAndRole() {
    String username = "admin";
    String password = "1234";
    LoginRequest request = new LoginRequest(username, password);

    Authorizer.Role expectedRole = Authorizer.Role.ROLE_ADMIN;

    AcceptingAuthenticatorSpy authenticator = new AcceptingAuthenticatorSpy();
    sessionGateway = new SessionGatewaySpy();
    loginInteractor = new LoginInteractor(sessionGateway, authenticator, null);

    Response<ViewableSession> response = loginInteractor.handle(request);

    ViewableSession viewableSession = response.getValues();

    assertEquals(expectedRole.toString(), viewableSession.role);
    assertEquals(authenticator.token, viewableSession.token);
    assertEquals(sessionGateway.addedSession.getToken(), viewableSession.token);
    assertEquals(expectedRole, sessionGateway.addedSession.getRole());
  }
}
