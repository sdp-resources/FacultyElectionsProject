package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.mock.gateway.session.RejectingSessionGatewayStub;
import fsc.request.ViewProfilesListRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class AuthorizeInteractorTest {
  @Test
  public void sessionGatewayWithNoTokensCanHandleAndGivesErrorResponse() {
    SessionGateway sessionGateway = new RejectingSessionGatewayStub();
    AuthorizeInteractor authorizeInteractor = new AuthorizeInteractor(sessionGateway);
    ViewProfilesListRequest request = new ViewProfilesListRequest();
    boolean canHandleRequest = authorizeInteractor.canHandle(request);
    Response response = authorizeInteractor.execute(request);

    assertEquals(ResponseFactory.notAuthorized(), response);
    assertTrue(canHandleRequest);
  }
}
