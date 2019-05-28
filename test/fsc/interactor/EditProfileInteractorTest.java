package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.EditProfileRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static fsc.request.EditProfileRequest.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class EditProfileInteractorTest {

  EditProfileRequest request;
  Map<String, Object> changes;
  Profile providedProfile;
  private ExistingProfileGatewaySpy profileGateway;
  private EditProfileInteractor interactor;

  @Before
  public void setup() {
    changes = new HashMap<>();
    providedProfile = new Profile("Bob Ross", "rossB12", "Arts and Letters", "Tenured");
    profileGateway = new ExistingProfileGatewaySpy(providedProfile);
    request = new EditProfileRequest("rossB12", changes);
    interactor = new EditProfileInteractor(profileGateway);
  }

  @Test
  public void noProfileExistsException() {
    changes.put(CHANGE_CONTRACT_TYPE, "Tenured");
    interactor = new EditProfileInteractor(new InvalidProfileGatewaySpy());
    Response response = interactor.execute(request);
    assertEquals(ErrorResponse.unknownProfileName(), response);
  }

  @Test
  public void canTakeProfile() {
    changes.put(CHANGE_CONTRACT_TYPE, "Untenured");
    Response response = interactor.execute(request);
    assertEquals(new SuccessResponse(), response);
  }

  @Test
  public void canEditSingleField() {
    changes.put(CHANGE_CONTRACT_TYPE, "Untenured");
    interactor.execute(request);
    assertNotEquals("Tenured", providedProfile.getContract());
  }

  @Test
  public void canHandleMultipleChanges() {
    changes.put(CHANGE_NAME, "Bill Mill");
    changes.put(CHANGE_DIVISION, "Science");
    changes.put(CHANGE_CONTRACT_TYPE, "Sabbatical");
    interactor.execute(request);
    assertEquals("Bill Mill", providedProfile.getName());
    assertEquals("Sabbatical", providedProfile.getContract());
    assertEquals("Science", providedProfile.getDivision());
  }

  @Test
  public void canHandleBooleanChanges() {
    changes.put(CHANGE_ACTIVE, false);
    interactor.execute(request);
    assertFalse(providedProfile.isActive());
  }

  @Test
  public void spyRemembersUsername() {
    changes.put(CHANGE_ACTIVE, "False");
    interactor.execute(request);
    assertFalse(providedProfile.isActive());
    assertEquals(request.username, profileGateway.providedUsername);
    assertTrue(ExistingProfileGatewaySpy.profileHasBeenEdited);
  }
}
