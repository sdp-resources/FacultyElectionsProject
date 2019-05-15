package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGatewayInterface;
import fsc.mock.noProfileGateWaySpy;
import fsc.request.EditProfileRequest;
import fsc.response.FailedSearchResponse;
import fsc.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class EditProfileInteractorTest {

  EditProfileRequest request;

  @Before
  public void setup(){
    Profile dummyProfile = new Profile("Bob Ross", "rossB12","Arts and Letters", "Tenured");
  }

  @Test
  public void noProfileExistsException() throws Exception {
    Map<String,String> changes = new HashMap<>();
    changes.put("Contract","Tenured");
    request = new EditProfileRequest("Username", changes);
    ProfileGatewayInterface fakeGateway = new noProfileGateWaySpy();
    EditProfileInteractor interactor = new EditProfileInteractor(fakeGateway);
    Response response = interactor.execute(request);
    assertTrue(response instanceof FailedSearchResponse);
  }

  @Test
  public void profileGetsEdited() throws Exception {
    Map<String, String> changes = new HashMap<>();
    changes.put("Contract", "Untenured");
    request = new EditProfileRequest("rossB12", changes);
    ProfileGatewayInterface fakeGateway = new noProfileGateWaySpy();


  }
}
