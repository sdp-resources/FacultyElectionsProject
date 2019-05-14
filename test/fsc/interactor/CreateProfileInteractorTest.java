package fsc.interactor;

import fsc.gateway.ProfileGatewayInterface;
import fsc.request.CreateProfileRequest;
import gateway.SpyGatewayNoProfileWithThatUsername;
import gateway.SpyGatewayProfileWithThatUsernameAlreadyExists;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateProfileInteractorTest {

  CreateProfileRequest request;
  ProfileGatewayInterface gateway;
  CreateProfileInteractor interactor;

  @Before
  public void setup() {
     request =  new CreateProfileRequest("Joe Hayfield", "hayfieldj", "ART",
                                                            "Part-Time");
  }

  @Test
  public void testCorrectExecute() throws Exception {
    gateway = new SpyGatewayNoProfileWithThatUsername();
    interactor = new CreateProfileInteractor(gateway);
    interactor.execute(request);
    assertEquals("hayfieldj", SpyGatewayNoProfileWithThatUsername.submittedUsername);
  }

  @Test
  public void testWrongUsernameExecute() throws Exception {
    gateway = new SpyGatewayProfileWithThatUsernameAlreadyExists();
    String failString = "Not Failed";
    try {interactor.execute(request);}
    catch(Exception e) {
      //assertEquals("Username Already Used!", e);
      failString = "Failed";
    }
    assertEquals("Failed", failString);
  }
}
