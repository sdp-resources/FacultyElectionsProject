package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.mock.NoProfileWithThatUsernameProfileGatewaySpy;
import fsc.request.EditProfileRequest;
import fsc.response.FailedSearchResponse;
import fsc.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class EditProfileInteractorTest {

  EditProfileRequest request;

  @Test
  public void noProfileExistsException() throws Exception {
    Map<String,String> changes = new HashMap<>();
    changes.put("Contract","Tenured");
    request = new EditProfileRequest("Username", changes);
    ProfileGateway fakeGateway = new NoProfileWithThatUsernameProfileGatewaySpy();
    EditProfileInteractor interactor = new EditProfileInteractor(fakeGateway);
    Response response = interactor.execute(request);
    assertTrue(response instanceof FailedSearchResponse);
  }
}
