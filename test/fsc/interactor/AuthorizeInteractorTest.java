package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.mock.gateway.session.RejectingSessionGatewayStub;
import fsc.request.ViewProfilesListRequest;
import fsc.response.ErrorResponse;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class AuthorizeInteractorTest {
  @Test
  public void sessionGatewayWithNoTokensCanHandleAndGivesErrorResponse()
  {
    SessionGateway sessionGateway = new RejectingSessionGatewayStub();
    Interactor authorizeInteractor = new AuthorizeInteractor(sessionGateway);
    ViewProfilesListRequest request = new ViewProfilesListRequest();
    boolean canHandleRequest = authorizeInteractor.canHandle(request);
    ErrorResponse response = (ErrorResponse) authorizeInteractor.execute(request);

    assertEquals("Session invalid", response.message);
    assertTrue(canHandleRequest);
  }
}
