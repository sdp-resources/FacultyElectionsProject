package fsc.interactor;

import fsc.response.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;

public class ProfileViewerInteractorTest {
  // This method creates a Hash Map
  // Has a request method that turns it into a response.
  @Test
  public void RequestErrorTest_NoUsername() throws ProfileReviewInteractor.NoProfileException {
    ProfileViewerRequest request = new ProfileViewerRequest("BoogieA14");
    noProfileGateWaySpy gateway = new noProfileGateWaySpy();
    ProfileReviewInteractor viewInteractor = new ProfileReviewInteractor(gateway);
    Response response = viewInteractor.execute(request);
    assertTrue(response instanceof ProfileReviewInteractor.ErrorResponse);
  }
}
