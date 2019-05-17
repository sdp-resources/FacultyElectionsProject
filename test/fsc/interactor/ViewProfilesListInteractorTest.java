package fsc.interactor;

import fsc.gateway.ProfileGateway;
import fsc.mock.ProfileGatewayStub;
import fsc.request.ViewProfilesListRequest;
import fsc.response.Response;
import fsc.response.ViewProfilesListResponse;
import fsc.viewable.ViewableProfile;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ViewProfilesListInteractorTest {

  @Ignore
  @Test
  public void canMakeProfilesListInteractor()
  {
    ProfileGatewayStub profileGateway = new ProfileGatewayStub();


    ViewProfilesListInteractor interactor = new ViewProfilesListInteractor(profileGateway);

    ViewProfilesListRequest request = new ViewProfilesListRequest();

    Response response = interactor.execute(request);

    assertTrue(response instanceof ViewProfilesListResponse);

  }

}
