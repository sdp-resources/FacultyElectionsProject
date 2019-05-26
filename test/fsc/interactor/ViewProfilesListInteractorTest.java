package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.ViewProfilesListRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableProfile;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

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
    Response generalResponse = interactor.execute(request);
    ViewResponse<List<ViewableProfile>> response = (ViewResponse<List<ViewableProfile>>) generalResponse;
    List<ViewableProfile> expectedViewableProfiles = getViewableProfiles(
          profileGatewaySpy.getAllProfiles());

    assertTrue(profileGatewaySpy.getAllProfilesWasCalled);
    assertEquals(expectedViewableProfiles, response.values);
  }

  private List<ViewableProfile> getViewableProfiles(List<Profile> profiles) {
    ViewableEntityConverter viewableEntityConverter = new ViewableEntityConverter();
    return profiles.stream().map(viewableEntityConverter::convert).collect(Collectors.toList());
  }
}
