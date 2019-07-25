package fsc.interactor;

import fsc.entity.EntityFactory;
import fsc.entity.Profile;
import fsc.entity.SimpleEntityFactory;
import fsc.mock.EntityStub;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.CreateProfileRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ProfileInteractorTest {

  CreateProfileRequest request;
  ProfileInteractor interactor;
  Response response;
  Profile providedProfile = EntityStub.getProfile(0);
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    request = new CreateProfileRequest("Joe Hayfield", "hayfieldj", "ART", "Part-Time");
  }

  @Test
  public void testCorrectExecute() {
    InvalidProfileGatewaySpy gateway = new InvalidProfileGatewaySpy();
    interactor = new ProfileInteractor(gateway, entityFactory);
    request.setSession(EntityStub.adminSession());
    response = interactor.handle(request);
    assertEquals(ResponseFactory.success(), response);
    assertEquals("hayfieldj", gateway.submittedUsername);
    assertTrue(gateway.hasSaved);
  }

  @Test
  public void testWrongUsernameExecute() {
    ExistingProfileGatewaySpy gateway = new ExistingProfileGatewaySpy(providedProfile);
    interactor = new ProfileInteractor(gateway, entityFactory);
    request.setSession(EntityStub.adminSession());
    response = interactor.handle(request);
    assertEquals(ResponseFactory.resourceExists(), response);
  }
}