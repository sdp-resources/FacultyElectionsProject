package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.EntityStub;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.CreateProfileRequest;
import fsc.response.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ProfileInteractorTest {

  CreateProfileRequest request;
  ProfileInteractor interactor;
  Response response;
  Profile providedProfile = EntityStub.getProfile(0);

  @Before
  public void setup() {
    request = new CreateProfileRequest("Joe Hayfield", "hayfieldj", "ART", "Part-Time");
  }

  @Test
  public void testCorrectExecute() {
    InvalidProfileGatewaySpy gateway = new InvalidProfileGatewaySpy();
    interactor = new ProfileInteractor(gateway);
    response = interactor.execute(request);
    assertEquals("hayfieldj", gateway.submittedUsername);
    assertEquals(ResponseFactory.success(), response);
    assertTrue(gateway.hasSaved);
  }

  @Test
  public void testWrongUsernameExecute() {
    ExistingProfileGatewaySpy gateway = new ExistingProfileGatewaySpy(providedProfile);
    interactor = new ProfileInteractor(gateway);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.resourceExists(), response);
  }
}