package fsc.interactor;

import fsc.mock.AllProfilesGatewaySpy;
import fsc.entity.Profile;
import fsc.mock.ViewableEntityConverterSpy;
import fsc.request.ViewProfilesListRequest;
import fsc.response.ViewResponse;
import fsc.service.Context;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableProfile;
import org.junit.*;

import java.util.ArrayList;
import java.util.Collection;
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
    ViewableEntityConverterSpy converterSpy = new ViewableEntityConverterSpy();
    Context.instance.viewableEntityConverter = converterSpy;

    AllProfilesGatewaySpy profileGatewaySpy = new AllProfilesGatewaySpy(profile1, profile2, profile3);

    ViewProfilesListInteractor interactor = new ViewProfilesListInteractor(profileGatewaySpy);

    ViewProfilesListRequest request = new ViewProfilesListRequest();

    ViewResponse<Collection<ViewableProfile>> response = (ViewResponse<Collection<ViewableProfile>>) interactor.execute(request);

    List<ViewableProfile> expectedProfiles = new ArrayList<>();
    for(Profile profile : profileGatewaySpy.getAllProfiles())
    {
      expectedProfiles.add(Context.instance.viewableEntityConverter.convert(profile));
    }

    assertTrue(profileGatewaySpy.getAllProfilesWasCalled);
    assertEquals(expectedProfiles, response.values);
    assertTrue(converterSpy.profileReceived != null);
  }

  @After
  public void Teardown()
  {
    restoreConverter();
  }

  private ViewableEntityConverter savedConverter;
  private void saveConverter()
  {
    savedConverter = Context.instance.viewableEntityConverter;
  }

  private void restoreConverter()
  {
    Context.instance.viewableEntityConverter = savedConverter;
  }

}
