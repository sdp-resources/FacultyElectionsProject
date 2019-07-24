package fsc.interactor;

import fsc.entity.EntityFactory;
import fsc.entity.Profile;
import fsc.entity.SimpleEntityFactory;
import fsc.mock.EntityStub;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.EditProfileRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EditProfileInteractorTest {
  private EditProfileRequest request;
  private Profile providedProfile;
  private ExistingProfileGatewaySpy profileGateway;
  private ProfileInteractor interactor;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    providedProfile = EntityStub.getProfile(0);
    profileGateway = new ExistingProfileGatewaySpy(providedProfile);
    request = new EditProfileRequest("rossB12");
    request.setSession(EntityStub.adminSession());
    interactor = new ProfileInteractor(profileGateway, entityFactory);
  }

  @Test
  public void noProfileExistsException() {
    request.changeContractType("Tenured");
    interactor = new ProfileInteractor(new InvalidProfileGatewaySpy(), entityFactory);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.unknownProfileName(), response);
  }

  @Test
  public void canTakeProfile() {
    request.changeContractType("Untenured");
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.success(), response);
  }

  @Test
  public void canEditSingleField() {
    request.changeContractType("Untenured");
    interactor.execute(request);
    assertNotEquals("Tenured", providedProfile.getContract());
  }

  @Test
  public void canHandleMultipleChanges() {
    request.changeFullname("Bill Mill");
    request.changeDivision("Science");
    request.changeContractType("Sabbatical");
    interactor.execute(request);
    assertEquals("Bill Mill", providedProfile.getName());
    assertEquals("Sabbatical", providedProfile.getContract());
    assertEquals("Science", providedProfile.getDivision());
  }

  @Test
  public void canTestStatus() {
    request.changeActiveStatus("inactive");
    interactor.execute(request);
    request.changeActiveStatus("active");
    interactor.execute(request);
    assertTrue(providedProfile.isActive());
    assertEquals(request.username, profileGateway.providedUsername);
    assertTrue(ExistingProfileGatewaySpy.profileHasBeenEdited);
  }

  @Test
  public void spyRemembersUsername() {
    request.changeActiveStatus("inactive");
    Response response = interactor.execute(request);
    assertTrue(response.isSuccessful());
    assertFalse(providedProfile.isActive());
    assertEquals(request.username, profileGateway.providedUsername);
    assertTrue(ExistingProfileGatewaySpy.profileHasBeenEdited);
  }

  @Test
  public void whenNonAdminUserIsLoggedIn_throwError() {
    request.setSession(EntityStub.userSession(providedProfile.getUsername()));
    request.changeActiveStatus("inactive");
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.notAuthorized(), response);
  }
}
