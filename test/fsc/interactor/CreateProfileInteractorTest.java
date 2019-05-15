package fsc.interactor;

import fsc.gateway.ProfileGatewayInterface;
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
  ProfileGatewayInterface gateway;
  CreateProfileInteractor interactor;
  Response response;


  @Before
  public void setup() {
     request =  new CreateProfileRequest("Joe Hayfield", "hayfieldj", "ART",
                                                            "Part-Time");
  }

  @Test
  public void testCorrectExecute() throws Exception {
    gateway = new SpyGatewayNoProfileWithThatUsername();
    interactor = new CreateProfileInteractor(gateway);
    response = interactor.execute(request);
    assertEquals("hayfieldj", SpyGatewayNoProfileWithThatUsername.submittedUsername);
    assertTrue(response instanceof SuccessfullyAddedProfileResponse);
  }

  @Test
  public void testWrongUsernameExecute() throws Exception {
    gateway = new SpyGatewayProfileWithThatUsernameAlreadyExists();
    interactor = new CreateProfileInteractor(gateway);
    response = interactor.execute(request);
    assertTrue(response instanceof FailedAddedProfileResponse);


  }
}
