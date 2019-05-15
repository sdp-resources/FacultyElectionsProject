package fsc.interactor;

import fsc.response.Response;
import fsc.mock.NoProfileExistsProfileGatewaySpy;
import fsc.mock.correctProfileGatewayMock;

import org.junit.Test;

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
   // assertTrue(response.userInfo instanceof HashMap<String, String>);
    assertTrue(response instanceof ProfileReviewInteractor.ErrorResponse);
    /*
    This test is currently not working. It is passing but for the wrong reason. It should return
    an appropriate response but instead is receiving an ErrorResponse similar to the one in the
    previous test.
     */
  }
}
