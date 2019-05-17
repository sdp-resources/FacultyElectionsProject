package fsc.interactor;

import fsc.request.ProfileViewerRequest;
import fsc.response.ErrorResponse;
import fsc.response.ProfileViewerResponse;
import fsc.response.Response;
import fsc.mock.NoProfileExistsProfileGatewaySpy;
import fsc.mock.correctProfileGatewayMock;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewProfileInteractorTest {
  @Test
  public void RequestErrorTest_NoUsername() {
    ProfileViewerRequest request = new ProfileViewerRequest("BoogieA14");
    NoProfileExistsProfileGatewaySpy gateway = new NoProfileExistsProfileGatewaySpy();
    ViewProfileInteractor viewInteractor = new ViewProfileInteractor(gateway);
    Response response = viewInteractor.execute(request);
    assertTrue(response instanceof ErrorResponse);
  }

  @Test
  public void validRequest(){
    ProfileViewerRequest request = new ProfileViewerRequest("BoogieA14");
    correctProfileGatewayMock gateway = new correctProfileGatewayMock();
    ViewProfileInteractor viewInteractor = new ViewProfileInteractor(gateway);
    Response response = viewInteractor.execute(request);
    assertEquals("BoogieA14", gateway.submittedUsername);
    assertTrue(response instanceof ProfileViewerResponse);
  }
}
