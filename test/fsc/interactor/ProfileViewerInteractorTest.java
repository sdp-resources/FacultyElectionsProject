package fsc.interactor;

import fsc.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ProfileViewerInteractorTest {
  @Test
  public void RequestErrorTest_NoUsername() {
    ProfileViewerRequest request = new ProfileViewerRequest("BoogieA14");
    noProfileGateWaySpy gateway = new noProfileGateWaySpy();
    ProfileReviewInteractor viewInteractor = new ProfileReviewInteractor(gateway);
    Response response = viewInteractor.execute(request);
    assertTrue(response instanceof ProfileReviewInteractor.ErrorResponse);
  }
}
