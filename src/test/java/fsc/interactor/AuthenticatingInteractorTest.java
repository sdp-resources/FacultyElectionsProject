package fsc.interactor;

import fsc.MyTime;
import fsc.entity.session.AuthenticatedSession;
import fsc.entity.session.UnauthenticatedSession;
import fsc.mock.SimpleInteractorSpy;
import fsc.mock.gateway.session.SessionGatewaySpy;
import fsc.request.Request;
import fsc.request.ViewContractsRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.Authorizer;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthenticatingInteractorTest {

  private SessionGatewaySpy sessionGatewaySpy;
  private AuthenticatingInteractor interactor;
  private Request request = new ViewContractsRequest();

  @Test
  public void whenNoTokenIsPresent_setSessionToUnauthenticated() {
    sessionGatewaySpy = new SessionGatewaySpy();
    interactor = new AuthenticatingInteractor(sessionGatewaySpy);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.cannotHandle(), response);
    assertFalse(request.getSession().isAuthenticated());
  }

  @Test
  public void whenTokenIsInvalid_returnError() {
    sessionGatewaySpy = new SessionGatewaySpy();
    interactor = new AuthenticatingInteractor(sessionGatewaySpy);
    request.token = "something";
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.invalidSession(), response);
  }

  @Test
  public void whenTokenIsValid_setSessionAndDelegate() {
    AuthenticatedSession session = currentSession();
    sessionGatewaySpy = new SessionGatewaySpy(session);
    SimpleInteractorSpy interactor2 = new SimpleInteractorSpy("1");
    interactor = (AuthenticatingInteractor) new AuthenticatingInteractor(sessionGatewaySpy)
                                                  .append(interactor2);
    request.token = session.getToken();
    interactor.handle(request);
    assertEquals(session, request.getSession());
    assertTrue(interactor2.handleCalled);
  }

  @Test
  public void whenSessionHasTimedOut_returnError() {
    AuthenticatedSession session = expiredSession();
    sessionGatewaySpy = new SessionGatewaySpy(session);
    interactor = new AuthenticatingInteractor(sessionGatewaySpy);
    request.token = session.getToken();
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.invalidSession(), response);
  }

  @Test
  public void whenRequestHasSessionAlreadySet_dontChangeIt() {
    AuthenticatedSession session = currentSession();
    sessionGatewaySpy = new SessionGatewaySpy(session);
    interactor = new AuthenticatingInteractor(sessionGatewaySpy);
    request.token = session.getToken();
    request.setSession(new UnauthenticatedSession());
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.cannotHandle(), response);
    assertFalse(request.getSession().isAuthenticated());
  }

  @Test
  public void whenSessionIsAuthenticated_ItsExpirationDateIsUpdatedToThirtyMinutesFromNow() {
    AuthenticatedSession session = currentSession();
    sessionGatewaySpy = new SessionGatewaySpy(session);
    interactor = new AuthenticatingInteractor(sessionGatewaySpy);
    request.token = "aToken";
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.cannotHandle(), response);
    assertEquals(sessionGatewaySpy.renewedSession, session);
  }

  private AuthenticatedSession currentSession() {
    return new AuthenticatedSession(Authorizer.Role.ROLE_ADMIN,
                                    "admin", "aToken",
                                    MyTime.fromNow(10));
  }

  private AuthenticatedSession expiredSession() {
    return new AuthenticatedSession(Authorizer.Role.ROLE_ADMIN,
                                    "admin", "aToken",
                                    MyTime.beforeNow(10));
  }

}