package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.ViewProfilesListRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ViewProfilesListInteractorTest {

  Profile profile1 = new Profile("Ben Givens", "givensb", "ART", "Tenured");
  Profile profile2 = new Profile("Jacob Stricker", "strickerj", "SCIENCE", "Tenured");
  Profile profile3 = new Profile("Blaise Lin", "linb", "SOCIAL", "Non-tenured");
  private ViewProfilesListRequest request;
  private ProfileGatewayStub profileGatewaySpy;

  @Before
  public void setup() {
    request = new ViewProfilesListRequest();
    profileGatewaySpy = new ProfileGatewayStub(profile1, profile2, profile3);
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
