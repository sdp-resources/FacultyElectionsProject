package fsc.interactor;

import fsc.mock.AllProfilesGatewaySpy;
import fsc.entity.Profile;
import fsc.mock.ProfileToViewableProfileConverterSpy;
import fsc.request.ViewProfilesListRequest;
import fsc.response.ViewProfilesListResponse;
import fsc.service.Context;
import fsc.service.ProfileToViewableProfileConverter;
import fsc.viewable.ViewableProfile;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ViewProfilesListInteractorTest {

  Profile profile1 = new Profile("Ben Givens", "givensb", "ART", "Tenured");
  Profile profile2 = new Profile("Jacob Stricker", "strickerj", "SCIENCE", "Tenured");
  Profile profile3 = new Profile("Blaise Lin", "linb", "SOCIAL", "Non-tenured");

  @Before
  public void setup()
  {
    saveConverter();
  }

  @Test
  public void profileGatewayHasNoErrorsResponseHasAllProfiles() {
    ProfileToViewableProfileConverterSpy converterSpy = new ProfileToViewableProfileConverterSpy();
    Context.instance.profileToViewableProfileConverter = converterSpy;

    AllProfilesGatewaySpy profileGatewaySpy = new AllProfilesGatewaySpy(profile1, profile2, profile3);

    ViewProfilesListInteractor interactor = new ViewProfilesListInteractor(profileGatewaySpy);

    ViewProfilesListRequest request = new ViewProfilesListRequest();

    ViewProfilesListResponse response = (ViewProfilesListResponse) interactor.execute(request);

    List<ViewableProfile> expectedProfiles = new ArrayList<>();
    for(Profile profile : profileGatewaySpy.getAllProfiles())
    {
      expectedProfiles.add(Context.instance.profileToViewableProfileConverter.convert(profile));
    }

    assertTrue(profileGatewaySpy.getAllProfilesWasCalled);
    assertEquals(expectedProfiles, response.viewableProfiles);
    assertTrue(converterSpy.profileReceived != null);
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
