package fsc.interactor;

import fsc.mock.*;
import fsc.gateway.ProfileGatewayInterface;
import fsc.mock.SpyGatewayNoProfileWithThatUsername;
import fsc.mock.SpyGatewayProfileWithThatUsernameAlreadyExists;
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
    SpyGatewayNoProfileWithThatUsername gateway = new SpyGatewayNoProfileWithThatUsername();
    interactor = new CreateProfileInteractor(gateway);
    response = interactor.execute(request);
    assertEquals("hayfieldj", gateway.submittedUsername);
    assertTrue(response instanceof SuccessfullyAddedProfileResponse);
  }

  @Test
  public void testWrongUsernameExecute() throws Exception {
    SpyGatewayProfileWithThatUsernameAlreadyExists gateway = new SpyGatewayProfileWithThatUsernameAlreadyExists();
    interactor = new CreateProfileInteractor(gateway);
    response = interactor.execute(request);
    assertTrue(response instanceof FailedAddedProfileResponse);
  }

  @Test
  public void testWrongDivision() throws  Exception {
    SpyGatewayInvalidDivision gateway = new SpyGatewayInvalidDivision();
    interactor = new CreateProfileInteractor(gateway);
    response = interactor.execute(request);
    assertEquals("ART", gateway.submittedDivision);
  }
}
