package fsc.interactor;

import fsc.entity.EntityFactory;
import fsc.entity.Profile;
import fsc.entity.SimpleEntityFactory;
import fsc.mock.EntityStub;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.ViewProfileRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewProfileInteractorTest {

  Profile profile = EntityStub.getProfile(0);
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Test
  public void validUsernameReturnsCorrectViewableProfile() {
    ExistingProfileGatewaySpy gatewaySpy = new ExistingProfileGatewaySpy(profile);

    ViewProfileRequest request = new ViewProfileRequest(profile.getUsername());
    ProfileInteractor interactor = new ProfileInteractor(gatewaySpy, entityFactory, null);
    Response response = interactor.handle(request);

    assertEquals(request.username, gatewaySpy.providedUsername);
    assertEquals(ResponseFactory.ofProfile(profile), response);
  }

  @Test
  public void whenViewingMissingProfile_returnErrorResponse() {
    InvalidProfileGatewaySpy gatewaySpy = new InvalidProfileGatewaySpy();

    ViewProfileRequest request = new ViewProfileRequest("BoogieA14");
    ProfileInteractor viewInteractor = new ProfileInteractor(gatewaySpy, entityFactory, null);
    Response response = viewInteractor.handle(request);

    assertEquals(request.username, gatewaySpy.submittedUsername);
    assertEquals(ResponseFactory.unknownProfileName(), response);
  }
}
