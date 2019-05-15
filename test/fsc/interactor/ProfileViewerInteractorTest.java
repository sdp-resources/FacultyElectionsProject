package fsc.interactor;

import fsc.request.ProfileViewerRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.mock.NoProfileExistsProfileGatewaySpy;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ProfileViewerInteractorTest {
  // This method creates a Hash Map
  // Has a request method that turns it into a response.
  @Test
  public void RequestErrorTest_NoUsername() {
    ProfileViewerRequest request = new ProfileViewerRequest("BoogieA14");
    NoProfileExistsProfileGatewaySpy gateway = new NoProfileExistsProfileGatewaySpy();
    ProfileReviewInteractor viewInteractor = new ProfileReviewInteractor(gateway);
    Response response = viewInteractor.execute(request);
    assertTrue(response instanceof ErrorResponse);
  }

}
