package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.*;
import fsc.request.ViewProfileRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ViewResponse;

import fsc.service.Context;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewProfileInteractorTest {

  Profile providedProfile = new Profile("Bob Ross", "rossB12", "Arts and Letters", "Tenured");

  @Before
  public void setup()
  {
    saveConverter();
  }

  @Test
  public void validUsernameReturnsCorrectViewableProfile() {
    ViewableEntityConverterSpy converterSpy = new ViewableEntityConverterSpy();
    Context.instance.viewableEntityConverter = converterSpy;
    Profile profile = new Profile("Boogie", "BoogieA14", "division", "contract");
    ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy gatewaySpy = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(providedProfile);

    ViewProfileRequest request = new ViewProfileRequest(profile.getUsername());
    ViewProfileInteractor viewInteractor = new ViewProfileInteractor(gatewaySpy);
    ViewResponse<ViewableProfile> response = (ViewResponse) viewInteractor.execute(request);

    assertEquals(request.username, gatewaySpy.providedUsername);
    assertEquals(gatewaySpy.profileSent, converterSpy.profileReceived);
    assertEquals(response.values,
                 Context.instance.viewableEntityConverter.convert(converterSpy.profileReceived));
  }

  @Test
  public void invalidUsernameDoesNotContinueReturnsErrorResponse()
  {
    ViewableEntityConverterSpy converterSpy = new ViewableEntityConverterSpy();
    Context.instance.viewableEntityConverter = converterSpy;

    InvalidProfileGatewaySpy gatewaySpy = new InvalidProfileGatewaySpy();

    ViewProfileRequest request = new ViewProfileRequest("BoogieA14");
    ViewProfileInteractor viewInteractor = new ViewProfileInteractor(gatewaySpy);
    Response response = viewInteractor.execute(request);

    assertEquals(request.username, gatewaySpy.usernameReceived);
    assertEquals(null, converterSpy.profileReceived);
    assertEquals(ErrorResponse.unknownProfileName(), response);
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
