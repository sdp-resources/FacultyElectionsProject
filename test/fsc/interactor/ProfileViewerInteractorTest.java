package fsc.interactor;

import fsc.gateway.Gateway;
import fsc.gateway.ProfileGatewayInterface;
import org.junit.Test;
import org.junit.runner.Request;
import spark.Response;

import java.util.Map;

public class ProfileViewerInteractorTest {
  // This method creates a Hash Map
  // Has a request method that turns it into a response.
  @Test
  public void RequestErrorTest_NoUsername(){
    ProfileViewerRequest req = new ProfileViewerRequest("BoogieA14");
    noProfileGateWaySpy gate = new noProfileGateWaySpy();
    //ProfileReviewInteractor viewInteractor = new ProfileReviewInteractor(gate);
    //ProfileViewerResponse res = viewInteractor.execute(req);
  }

}
