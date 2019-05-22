package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.mock.gateway.session.SessionGatewayDummy;
import fsc.request.LoginRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.service.Authorizer;
import fsc.service.authorizer.RejectingAuthorizerStub;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginInteractorTest {
  @Test
  public void notAuthorizedGivesError() {
    String username = "admin";
    String password = "1234";
    LoginRequest request =  new LoginRequest(username,password);

    Authorizer authorizer = new RejectingAuthorizerStub();
    SessionGateway sessionGateway = new SessionGatewayDummy();
    LoginInteractor loginInteractor = new LoginInteractor(sessionGateway, authorizer);

    ErrorResponse response = (ErrorResponse) loginInteractor.execute(request);

    assertEquals("Invalid username or password", response.response);
  }
}
