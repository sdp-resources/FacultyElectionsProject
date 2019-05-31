package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.EntityStub;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.ViewProfilesListRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ViewProfilesListInteractorTest {

  private ViewProfilesListRequest request;
  private ProfileGatewayStub profileGatewaySpy;
  private Profile profile1;
  private Profile profile2;
  private Profile profile3;

  @Before
  public void setup() {
    request = new ViewProfilesListRequest();
    profile1 = EntityStub.getProfile(0);
    profile2 = EntityStub.getProfile(1);
    profile3 = EntityStub.getProfile(2);
    profileGatewaySpy = new ProfileGatewayStub(profile1, profile2, profile3);
  }

  @Test
  public void profileGatewayHasNoErrorsResponseHasAllProfiles() {
    ViewProfilesListInteractor interactor = new ViewProfilesListInteractor(profileGatewaySpy);
    Response response = interactor.handle(request);
    Response expectedResponse = ViewResponse.ofProfileList(profileGatewaySpy.getAllProfiles());
    assertTrue(profileGatewaySpy.getAllProfilesWasCalled);
    assertEquals(expectedResponse, response);
  }

  @Test
  public void whenSetToViewActiveProfiles_ignoreInactiveProfiles() {
    ViewProfilesListInteractor interactor = new ViewProfilesListInteractor(profileGatewaySpy);
    profile2.setInactive();
    request = new ViewProfilesListRequest("status equals active");
    Response response = interactor.handle(request);
    Response expectedResponse = ViewResponse.ofProfileList(List.of(profile1, profile3));
    assertEquals(expectedResponse, response);
  }
}
