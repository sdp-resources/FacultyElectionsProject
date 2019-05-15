package fsc.interactor;

import fsc.mock.NoProfileWithThatUsernameProfileGatewaySpy;
import fsc.mock.ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy;
import fsc.request.CreateProfileRequest;
import fsc.response.FailedAddedProfileResponse;
import fsc.response.SuccessfullyAddedProfileResponse;
import org.junit.Before;
import org.junit.Test;
import fsc.response.Response;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CreateProfileInteractorTest {

  CreateProfileRequest request;
  CreateProfileInteractor interactor;
  Response response;


  @Before
  public void setup() {
     request =  new CreateProfileRequest("Joe Hayfield", "hayfieldj", "ART",
                                                            "Part-Time");
  }

  @Test
  public void testCorrectExecute() throws Exception {
    NoProfileWithThatUsernameProfileGatewaySpy gateway = new NoProfileWithThatUsernameProfileGatewaySpy();
    interactor = new CreateProfileInteractor(gateway);
    response = interactor.execute(request);
    assertEquals("hayfieldj", gateway.submittedUsername);
    assertTrue(response instanceof SuccessfullyAddedProfileResponse);
  }

  @Test
  public void testWrongUsernameExecute() throws Exception {
    ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy gateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy();
    interactor = new CreateProfileInteractor(gateway);
    response = interactor.execute(request);
    assertTrue(response instanceof FailedAddedProfileResponse);


  }
}
