package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.NoProfileWithThatUsernameProfileGatewaySpy;
import fsc.mock.ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy;
import fsc.request.EditProfileRequest;
import fsc.response.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class EditProfileInteractorTest {

  EditProfileRequest request;
  Map<String,Object> changes;
  final Profile providedProfile = new Profile("Bob Ross", "rossB12", "Arts and Letters", "Tenured");

  @Before
  public void setup(){
    changes = new HashMap<>();
  }

  @Test
  public void noProfileExistsException() {
    changes.put("Contract","Tenured");
    request = new EditProfileRequest("dummyUsername", changes);
    NoProfileWithThatUsernameProfileGatewaySpy fakeGateway = new NoProfileWithThatUsernameProfileGatewaySpy();
    EditProfileInteractor interactor = new EditProfileInteractor(fakeGateway);
    Response response = interactor.execute(request);
    assertEquals("No profile with that username exists", ((ErrorResponse)response).message);
  }

  @Test
  public void canTakeProfile() {
    changes.put("Contract", "Untenured");
    request = new EditProfileRequest("rossB12", changes);
    ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy fakegateway =
          new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(providedProfile);
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    Response response = interactor.execute(request);
    assertTrue(response instanceof SuccessResponse);
  }

  @Test
  public void canEditSingleField() {
    changes.put("Contract", "Untenured");
    request = new EditProfileRequest("rossB12", changes);
    ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy fakegateway =
          new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(providedProfile);
    Profile profile = fakegateway.getProfile("rossB12");
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertNotEquals("Tenured", profile.getContract());
  }

  @Test
  public void canHandleMultipleChanges() {
    changes.put("Name", "Bill Mill");
    changes.put("Division", "Science");
    changes.put("Contract", "Sabbatical");
    request = new EditProfileRequest("rossB12", changes);
    ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy fakegateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(providedProfile);
    Profile profile = fakegateway.getProfile("rossB12");
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertEquals("Bill Mill", profile.getName());
    assertEquals("Sabbatical", profile.getContract());
    assertEquals("Science", profile.getDivision());
  }

  @Test
  public void canHandleBooleanChanges() {
    changes.put("Inactive", true);
    request = new EditProfileRequest("rossB12", changes);
    ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy fakegateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(providedProfile);
    Profile profile = fakegateway.getProfile("rossB12");
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertFalse(profile.isActive());
  }

  @Test
  public void spyRemembersUsername() {
    changes.put("Inactive", "True");
    request = new EditProfileRequest("rossB12", changes);
    Profile providedProfile = new Profile("Bob Ross", "rossB12", "Arts and Letters", "Tenured");
    ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy fakegateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(providedProfile);
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertEquals(request.username, fakegateway.providedUsername);
  }

  @Test
  public void spyCanTellProfileHasBeenEdited() {
    changes.put("Inactive", "True");
    request = new EditProfileRequest("rossB12", changes);
    ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy fakegateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(providedProfile);
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertTrue(fakegateway.profileHasBeenEdited);
  }
}
