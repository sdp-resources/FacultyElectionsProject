package fsc.service;

import fsc.entity.Profile;
import fsc.interactor.AddDivisionInteractor;
import fsc.interactor.ViewProfileInteractor;
import fsc.mock.MissingDivisionGatewaySpy;
import fsc.mock.ProfileGatewayStub;
import fsc.request.AddDivisionRequest;
import fsc.request.ViewProfileRequest;
import fsc.response.*;
import fsc.viewable.ViewableProfile;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RequestRouterTest {

  private RequestRouter requestRouter;

  @Before
  public void setUp() {
    requestRouter = new RequestRouter(new AddDivisionInteractor(new MissingDivisionGatewaySpy()),
                                      new ViewProfileInteractor(new ProfileGatewayStub(
                                            new Profile("James Gordon", "jamesg", "ART",
                                                        "TENURED"))));
  }

  @Test
  public void emptyRouterGivesErrorResponse() {
    requestRouter = new RequestRouter();
    AddDivisionRequest addDivisionRequest = new AddDivisionRequest("SQUAD");
    ErrorResponse response = (ErrorResponse) requestRouter.execute(addDivisionRequest);
    assertEquals(ErrorResponse.cannotHandle(), response);
  }

  @Test
  public void canExecuteAddDivisionRequest() {
    AddDivisionRequest addDivisionRequest = new AddDivisionRequest("SQUAD");
    Response response = requestRouter.execute(addDivisionRequest);
    assertEquals(new SuccessResponse(), response);
  }

  @Test
  public void canExecuteViewProfileInteractor() {
    String username = "jamesg";
    ViewProfileRequest viewProfileRequest = new ViewProfileRequest(username);
    ViewResponse<ViewableProfile> response = (ViewResponse<ViewableProfile>) requestRouter.execute(
          viewProfileRequest);
    assertEquals(username, response.values.username);
  }
}
