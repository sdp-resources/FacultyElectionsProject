package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.noProfileGateWaySpy;
import fsc.mock.profileWasEditedGatewaySpy;
import fsc.request.EditProfileRequest;
import fsc.response.FailedSearchResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyEditedResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class EditProfileInteractorTest {

  EditProfileRequest request;
  Map<String,Object> changes;

  @Before
  public void setup(){
    changes = new HashMap<>();
  }

  @Test
  public void noProfileExistsException() throws Exception {
    changes.put("Contract","Tenured");
    request = new EditProfileRequest("dummyUsername", changes);
    noProfileGateWaySpy fakeGateway = new noProfileGateWaySpy();
    EditProfileInteractor interactor = new EditProfileInteractor(fakeGateway);
    Response response = interactor.execute(request);
    assertTrue(response instanceof FailedSearchResponse);
  }

  @Test
  public void canTakeProfile() throws Exception {
    changes.put("Contract", "Untenured");
    request = new EditProfileRequest("rossB12", changes);
    profileWasEditedGatewaySpy fakegateway = new profileWasEditedGatewaySpy();
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    Response response = interactor.execute(request);
    assertTrue(response instanceof SuccessfullyEditedResponse);
  }

  @Test
  public void canEditSingleField() throws Exception {
    changes.put("Contract", "Untenured");
    request = new EditProfileRequest("rossB12", changes);
    profileWasEditedGatewaySpy fakegateway = new profileWasEditedGatewaySpy();
    Profile profile = fakegateway.getProfileFromUsername("rossB12");
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertNotEquals("Tenured", profile.getContract());
  }

  @Test
  public void canHandleMultipleChanges() throws Exception {
    changes.put("Name", "Bill Mill");
    changes.put("Division", "Science");
    changes.put("Contract", "Sabbatical");
    request = new EditProfileRequest("rossB12", changes);
    profileWasEditedGatewaySpy fakegateway = new profileWasEditedGatewaySpy();
    Profile profile = fakegateway.getProfileFromUsername("rossB12");
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertEquals("Bill Mill", profile.getName());
    assertEquals("Sabbatical", profile.getContract());
    assertEquals("Science", profile.getDivision());
  }

  @Test
  public void canHandleBooleanChanges() throws Exception {
    changes.put("Inactive", true);
    request = new EditProfileRequest("rossB12", changes);
    profileWasEditedGatewaySpy fakegateway = new profileWasEditedGatewaySpy();
    Profile profile = fakegateway.getProfileFromUsername("rossB12");
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertFalse(profile.isActive());
  }

  @Test
  public void spyRemembersUsername() throws Exception {
    changes.put("Inactive", "True");
    request = new EditProfileRequest("rossB12", changes);
    profileWasEditedGatewaySpy fakegateway = new profileWasEditedGatewaySpy();
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertEquals(request.username, profileWasEditedGatewaySpy.providedUsername);
  }

  @Test
  public void spyCanTellProfileHasBeenEdited() throws Exception {
    changes.put("Inactive", "True");
    request = new EditProfileRequest("rossB12", changes);
    profileWasEditedGatewaySpy fakegateway = new profileWasEditedGatewaySpy();
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertTrue(profileWasEditedGatewaySpy.profileHasBeenEdited);
  }
}
