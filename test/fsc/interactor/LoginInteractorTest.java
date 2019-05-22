package fsc.interactor;

import fsc.gateway.SessionGateway;
import fsc.mock.gateway.session.SessionGatewayDummy;
import fsc.request.LoginRequest;
import fsc.response.Response;
import fsc.service.Authorizer;
import fsc.service.authorizer.AuthorizerDummy;
import org.junit.Test;

public class LoginInteractorTest {
  @Test
  public void canCreateInteractor() {
    String username = "admin";
    String password = "1234";
    LoginRequest request =  new LoginRequest(username,password);

    Authorizer authorizer = new AuthorizerDummy();
    SessionGateway sessionGateway = new SessionGatewayDummy();
    LoginInteractor loginInteractor = new LoginInteractor(sessionGateway, authorizer);

    Response response = loginInteractor.execute(request);
  }
}
