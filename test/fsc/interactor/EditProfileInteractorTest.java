package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.noProfileGateWaySpy;
import fsc.mock.profileWasEditedGatewaySpy;
import fsc.request.EditProfileRequest;
import fsc.response.FailedSearchResponse;
import fsc.response.Response;
import fsc.response.SuccessfullyEditedResponse;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class EditProfileInteractorTest {

  EditProfileRequest request;
  Map<String,String> changes;

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
  public void checkProfileforContractEdit() throws Exception {
    changes.put("Contract", "Untenured");
    request = new EditProfileRequest("rossB12", changes);
    profileWasEditedGatewaySpy fakegateway = new profileWasEditedGatewaySpy();
    Profile profile = fakegateway.getProfileFromUsername("rossB12");
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertNotEquals("Tenured", profile.getContract());
  }

  @Test
  public void checkProfileforDivisionEdit() throws Exception {
    changes.put("Division", "Computer Science");
    request = new EditProfileRequest("rossB12", changes);
    profileWasEditedGatewaySpy fakegateway = new profileWasEditedGatewaySpy();
    Profile profile = fakegateway.getProfileFromUsername("rossB12");
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertNotEquals("Arts and Letters", profile.getDivision());
  }

  @Test
  public void checkProfileforNameEdit() throws Exception {
    changes.put("Name", "Bill Mill");
    request = new EditProfileRequest("rossB12", changes);
    profileWasEditedGatewaySpy fakegateway = new profileWasEditedGatewaySpy();
    Profile profile = fakegateway.getProfileFromUsername("rossB12");
    EditProfileInteractor interactor = new EditProfileInteractor(fakegateway);
    interactor.execute(request);
    assertNotEquals("Bob Ross", profile.getName());
  }



}
