package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.*;
import fsc.request.ProfileViewerRequest;
import fsc.response.ErrorResponse;
import fsc.response.ViewProfileResponse;

import fsc.service.Context;
import fsc.service.ProfileToViewableProfileConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewProfileInteractorTest {

  @Before
  public void setup()
  {
    saveConverter();
  }

  @Test
  public void validUsernameReturnsCorrectViewableProfile() {
    ProfileToViewableProfileConverterSpy converterSpy = new ProfileToViewableProfileConverterSpy();
    Context.instance.profileToViewableProfileConverter = converterSpy;
    Profile profile = new Profile("Boogie", "BoogieA14", "division", "contract");
    ValidProfileGatewaySpy gatewaySpy = new ValidProfileGatewaySpy(profile);

    ProfileViewerRequest request = new ProfileViewerRequest(profile.getUsername());
    ViewProfileInteractor viewInteractor = new ViewProfileInteractor(gatewaySpy);
    ViewProfileResponse response = (ViewProfileResponse) viewInteractor.execute(request);

    assertEquals(request.username, gatewaySpy.usernameReceived);
    assertEquals(gatewaySpy.profileSent, converterSpy.profileReceived);
    assertEquals(response.viewableProfile,
                 Context.instance.profileToViewableProfileConverter.convert(converterSpy.profileReceived));
  }

  @Test
  public void invalidUsernameDoesNotContinueReturnsErrorResponse()
  {
    ProfileToViewableProfileConverterSpy converterSpy = new ProfileToViewableProfileConverterSpy();
    Context.instance.profileToViewableProfileConverter = converterSpy;

    InvalidProfileGatewaySpy gatewaySpy = new InvalidProfileGatewaySpy();

    ProfileViewerRequest request = new ProfileViewerRequest("BoogieA14");
    ViewProfileInteractor viewInteractor = new ViewProfileInteractor(gatewaySpy);
    ErrorResponse response = (ErrorResponse) viewInteractor.execute(request);

    assertEquals(request.username, gatewaySpy.usernameReceived);
    assertEquals(null, converterSpy.profileReceived);
    assertEquals(ErrorResponse.NO_PROFILE_FOUND, response.response);
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
