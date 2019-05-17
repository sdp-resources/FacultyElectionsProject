package fsc.interactor;

import fsc.mock.AllProfilesGatewaySpy;
import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.mock.ProfileGatewayStub;
import fsc.request.ViewProfilesListRequest;
import fsc.response.Response;
import fsc.response.ViewProfilesListResponse;
import fsc.service.Context;
import fsc.service.ProfileToViewableProfileConverter;
import org.junit.*;

import static junit.framework.TestCase.assertTrue;

public class ViewProfilesListInteractorTest {

  @Before
  public void setup()
  {
    saveConverter();
  }

  @Test
  public void canMakeProfilesListInteractor() {
    AllProfilesGatewaySpy profileGatewaySpy = new AllProfilesGatewaySpy();

    ViewProfilesListInteractor interactor = new ViewProfilesListInteractor(profileGatewaySpy);

    ViewProfilesListRequest request = new ViewProfilesListRequest();

    Response response = interactor.execute(request);

    assertTrue(profileGatewaySpy.getAllProfilesWasCalled);
    assertTrue(response instanceof ViewProfilesListResponse);
  }

  @After
  public void Teardown()
  {
    restoreConverter();
  }

  private ProfileToViewableProfileConverter savedConverter;
  private void saveConverter()
  {
    savedConverter = Context.instance.profileToViewableProfileConverter;
  }

  private void restoreConverter()
  {
    Context.instance.profileToViewableProfileConverter = savedConverter;
  }

}
