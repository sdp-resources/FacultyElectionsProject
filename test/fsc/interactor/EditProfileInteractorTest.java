package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.EditProfileRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class EditProfileInteractorTest {

  EditProfileRequest request;
  Map<String, Object> changes;
  Profile providedProfile;
  private ProfileGatewayStub profileGateway;

  @Before
  public void setup() {
    changes = new HashMap<>();
    providedProfile = new Profile("Bob Ross", "rossB12", "Arts and Letters", "Tenured");
    profileGateway = new ProfileGatewayStub(providedProfile);
  }

  @Test
  public void noProfileExistsException() {
    changes.put("Contract", "Tenured");
    request = new EditProfileRequest("dummyUsername", changes);
    EditProfileInteractor interactor = new EditProfileInteractor(new InvalidProfileGatewaySpy());
    Response response = interactor.execute(request);
    assertEquals(ErrorResponse.unknownProfileName(), response);
  }

  @Test
  public void canTakeProfile() {
    changes.put("Contract", "Untenured");
    request = new EditProfileRequest("rossB12", changes);
    EditProfileInteractor interactor = new EditProfileInteractor(profileGateway);
    Response response = interactor.execute(request);
    assertTrue(response instanceof SuccessResponse);
  }

  @Test
  public void canEditSingleField() {
    changes.put("Contract", "Untenured");
    request = new EditProfileRequest("rossB12", changes);
    EditProfileInteractor interactor = new EditProfileInteractor(profileGateway);
    interactor.execute(request);
    assertNotEquals("Tenured", providedProfile.getContract());
  }

  @Test
  public void canHandleMultipleChanges() {
    changes.put("Name", "Bill Mill");
    changes.put("Division", "Science");
    changes.put("Contract", "Sabbatical");
    request = new EditProfileRequest("rossB12", changes);
    EditProfileInteractor interactor = new EditProfileInteractor(profileGateway);
    interactor.execute(request);
    assertEquals("Bill Mill", providedProfile.getName());
    assertEquals("Sabbatical", providedProfile.getContract());
    assertEquals("Science", providedProfile.getDivision());
  }

  @Test
  public void canHandleBooleanChanges() {
    changes.put("Inactive", true);
    request = new EditProfileRequest("rossB12", changes);
    EditProfileInteractor interactor = new EditProfileInteractor(profileGateway);
    interactor.execute(request);
    assertFalse(providedProfile.isActive());
  }

  @Test
  public void spyRemembersUsername() {
    changes.put("Inactive", "True");
    request = new EditProfileRequest("rossB12", changes);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(providedProfile);
    ;
    EditProfileInteractor interactor = new EditProfileInteractor(profileGateway);
    interactor.execute(request);
    assertEquals(request.username, profileGateway.providedUsername);
  }

  @Test
  public void spyCanTellProfileHasBeenEdited() {
    changes.put("Inactive", "True");
    request = new EditProfileRequest("rossB12", changes);
    ExistingProfileGatewaySpy profileGateway = new ExistingProfileGatewaySpy(providedProfile);
    ;
    EditProfileInteractor interactor = new EditProfileInteractor(profileGateway);
    interactor.execute(request);
    assertTrue(ExistingProfileGatewaySpy.profileHasBeenEdited);
  }
}
