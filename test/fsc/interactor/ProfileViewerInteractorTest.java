package fsc.interactor;

import fsc.request.ProfileViewerRequest;
import fsc.response.ProfileViewerResponse;
import fsc.response.Response;
import fsc.mock.NoProfileExistsProfileGatewaySpy;
import fsc.mock.correctProfileGatewayMock;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProfileViewerInteractorTest {
  @Test
  public void RequestErrorTest_NoUsername() {
    ProfileViewerRequest request = new ProfileViewerRequest("BoogieA14");
    NoProfileExistsProfileGatewaySpy gateway = new NoProfileExistsProfileGatewaySpy();
    ProfileReviewInteractor viewInteractor = new ProfileReviewInteractor(gateway);
    Response response = viewInteractor.execute(request);
    assertTrue(response instanceof ProfileReviewInteractor.ErrorResponse);
  }

  @Test
  public void validRequest(){
    ProfileViewerRequest request = new ProfileViewerRequest("BoogieA14");
    correctProfileGatewayMock gateway = new correctProfileGatewayMock();
    ProfileReviewInteractor viewInteractor = new ProfileReviewInteractor(gateway);
    Response response = viewInteractor.execute(request);
    assertEquals("BoogieA14", gateway.submittedUsername);
//    assert()
    assertTrue(response instanceof ProfileViewerResponse);

  }
}
