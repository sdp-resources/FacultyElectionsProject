package fsc.service;

import fsc.entity.Profile;
import fsc.gateway.DivisionGateway;
import fsc.interactor.AddDivisionInteractor;
import fsc.interactor.ViewProfileInteractor;
import fsc.mock.MissingDivisionGatewaySpy;
import fsc.mock.ProfileGatewayStub;
import fsc.mock.ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy;
import fsc.request.AddDivisionRequest;
import fsc.request.ViewProfileRequest;
import fsc.response.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class RequestRouterTest {

  private RequestRouter requestRouter;

  @Before
  public void setUp() throws Exception {
    requestRouter = new RequestRouter(new AddDivisionInteractor(new MissingDivisionGatewaySpy()),
                                      new ViewProfileInteractor(new ProfileGatewayStub(
                                            new Profile("James Gordon", "jamesg", "ART", "TENURED"))));
  }

  @Test
  public void emptyRouterGivesErrorResponse()
  {
    requestRouter = new RequestRouter();
    AddDivisionRequest addDivisionRequest = new AddDivisionRequest("SQUAD");
    ErrorResponse response = (ErrorResponse) requestRouter.execute(addDivisionRequest);
    assertEquals("No interactor can handle that request", response.response);
  }

  @Test
  public void canExecuteAddDivisionRequest()
  {
    AddDivisionRequest addDivisionRequest = new AddDivisionRequest("SQUAD");
    Response response = requestRouter.execute(addDivisionRequest);
    assertTrue(response instanceof SuccessfullyAddedDivision);
  }

  @Test
  public void canExecuteViewProfileInteractor()
  {
    String username = "jamesg";
    ViewProfileRequest viewProfileRequest = new ViewProfileRequest(username);
    ViewProfileResponse response = (ViewProfileResponse) requestRouter.execute(viewProfileRequest);
    assertEquals(username, response.viewableProfile.username);
  }
}
