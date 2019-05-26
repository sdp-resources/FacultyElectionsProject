package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.ViewProfileRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ViewResponse;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableProfile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewProfileInteractorTest {

  Profile providedProfile = new Profile("Bob Ross", "rossB12", "Arts and Letters", "Tenured");

  @Test
  public void validUsernameReturnsCorrectViewableProfile() {
    ExistingProfileGatewaySpy gatewaySpy = new ExistingProfileGatewaySpy(
          providedProfile);

    ViewProfileRequest request = new ViewProfileRequest(providedProfile.getUsername());
    ViewProfileInteractor interactor = new ViewProfileInteractor(gatewaySpy);
    ViewResponse<ViewableProfile> response = (ViewResponse<ViewableProfile>) interactor.execute(request);

    assertEquals(request.username, gatewaySpy.providedUsername);
    assertEquals(response.values,
                 new ViewableEntityConverter().convert(providedProfile));
  }

  @Test
  public void whenViewingMissingProfile_returnErrorResponse() {
    InvalidProfileGatewaySpy gatewaySpy = new InvalidProfileGatewaySpy();

    ViewProfileRequest request = new ViewProfileRequest("BoogieA14");
    ViewProfileInteractor viewInteractor = new ViewProfileInteractor(gatewaySpy);
    Response response = viewInteractor.execute(request);

    assertEquals(request.username, gatewaySpy.submittedUsername);
    assertEquals(ErrorResponse.unknownProfileName(), response);
  }
}
