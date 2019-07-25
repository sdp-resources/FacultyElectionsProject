package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.mock.gateway.session.RejectingSessionGatewayStub;
import fsc.request.ViewProfilesListRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthorizeInteractorTest {
  @Test
  public void sessionGatewayWithNoTokensCanHandleAndGivesErrorResponse() {
    SessionGateway sessionGateway = new RejectingSessionGatewayStub();
    SessionInteractor authorizeInteractor = new SessionInteractor(sessionGateway);
    ViewProfilesListRequest request = new ViewProfilesListRequest();
    Response response = authorizeInteractor.handle(request);

    assertEquals(ResponseFactory.notAuthorized(), response);
  }
}
