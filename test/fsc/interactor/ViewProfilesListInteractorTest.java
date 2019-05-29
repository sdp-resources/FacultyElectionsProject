package fsc.interactor;

import fsc.mock.EntityStub;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.ViewProfilesListRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ViewProfilesListInteractorTest {

  private ViewProfilesListRequest request;
  private ProfileGatewayStub profileGatewaySpy;

  @Before
  public void setup() {
    request = new ViewProfilesListRequest();
    profileGatewaySpy = new ProfileGatewayStub(EntityStub.getProfile(0), EntityStub.getProfile(1),
                                               EntityStub.getProfile(2));
  }

  @Test
  public void profileGatewayHasNoErrorsResponseHasAllProfiles() {
    ViewProfilesListInteractor interactor = new ViewProfilesListInteractor(profileGatewaySpy);
    Response response = interactor.execute(request);
    Response expectedResponse = ViewResponse.ofProfileList(profileGatewaySpy.getAllProfiles());
    assertTrue(profileGatewaySpy.getAllProfilesWasCalled);
    assertEquals(expectedResponse, response);
  }

}
