package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.EntityStub;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.ViewProfileRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ViewResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewProfileInteractorTest {

  Profile profile = EntityStub.getProfile(0);

  @Test
  public void validUsernameReturnsCorrectViewableProfile() {
    ExistingProfileGatewaySpy gatewaySpy = new ExistingProfileGatewaySpy(profile);

    ViewProfileRequest request = new ViewProfileRequest(profile.getUsername());
    ProfileInteractor interactor = new ProfileInteractor(gatewaySpy);
    Response response = interactor.execute(request);

    assertEquals(request.username, gatewaySpy.providedUsername);
    assertEquals(ViewResponse.ofProfile(profile), response);
  }

  @Test
  public void whenViewingMissingProfile_returnErrorResponse() {
    InvalidProfileGatewaySpy gatewaySpy = new InvalidProfileGatewaySpy();

    ViewProfileRequest request = new ViewProfileRequest("BoogieA14");
    ProfileInteractor viewInteractor = new ProfileInteractor(gatewaySpy);
    Response response = viewInteractor.execute(request);

    assertEquals(request.username, gatewaySpy.submittedUsername);
    assertEquals(ErrorResponse.unknownProfileName(), response);
  }
}
