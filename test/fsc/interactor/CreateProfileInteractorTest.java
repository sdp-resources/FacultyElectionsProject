package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.NoProfileWithThatUsernameProfileGatewaySpy;
import fsc.mock.ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy;
import fsc.request.CreateProfileRequest;
import fsc.response.*;
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
    NoProfileWithThatUsernameProfileGatewaySpy gateway = new NoProfileWithThatUsernameProfileGatewaySpy();
    interactor = new CreateProfileInteractor(gateway);
    response = interactor.execute(request);
    assertEquals("hayfieldj", gateway.submittedUsername);
    assertTrue(response instanceof SuccessResponse);
  }

  @Test
  public void testWrongUsernameExecute() {
    ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy gateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(providedProfile);
    interactor = new CreateProfileInteractor(gateway);
    response = interactor.execute(request);
    assertEquals("Profile with that username already exists", ((ErrorResponse)response).message);
  }
}