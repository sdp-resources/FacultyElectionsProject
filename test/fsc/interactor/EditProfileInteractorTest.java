package fsc.interactor;

import fsc.entity.Profile;
import fsc.gateway.Gateway;
import fsc.request.EditProfileRequest;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class EditProfileInteractorTest {

  EditProfileRequest request;

  @Test
  public void canRun() {

  }

//  @Test
//  public void noProfileExistsException() {
//    request = new EditProfileRequest("Username", "B");
//    Gateway fakeGateway = new NoProfileGatewaySpy();
//    EditProfileInteractor interactor = new EditProfileInteractor(fakeGateway);
//    Response response = interactor.execute(request);
//    assertTrue(response instanceof FailedSearchResponse);
//  }
}
