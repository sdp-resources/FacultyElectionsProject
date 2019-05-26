package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.CreateProfileRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CreateProfileInteractorTest {

  CreateProfileRequest request;
  CreateProfileInteractor interactor;
  Response response;
  Profile providedProfile = new Profile("Bob Ross", "rossB12", "Arts and Letters", "Tenured");

  @Before
  public void setup() {
    request = new CreateProfileRequest("Joe Hayfield", "hayfieldj", "ART", "Part-Time");
  }

  @Test
  public void testCorrectExecute() {
    InvalidProfileGatewaySpy gateway = new InvalidProfileGatewaySpy();
    interactor = new CreateProfileInteractor(gateway);
    response = interactor.execute(request);
    assertEquals("hayfieldj", gateway.submittedUsername);
    assertTrue(response instanceof SuccessResponse);
  }

  @Test
  public void testWrongUsernameExecute() {
    ExistingProfileGatewaySpy gateway = new ExistingProfileGatewaySpy(providedProfile);
    interactor = new CreateProfileInteractor(gateway);
    response = interactor.execute(request);
    assertEquals(ErrorResponse.resourceExists(), response);
  }
}