package fsc.interactor;

import fsc.entity.Profile;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.request.EditProfileRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EditProfileInteractorTest {
  private EditProfileRequest request;
  private Profile providedProfile;
  private ExistingProfileGatewaySpy profileGateway;
  private EditProfileInteractor interactor;

  @Before
  public void setup() {
    providedProfile = new Profile("Bob Ross", "rossB12", "Arts and Letters", "Tenured");
    profileGateway = new ExistingProfileGatewaySpy(providedProfile);
    request = new EditProfileRequest("rossB12");
    interactor = new EditProfileInteractor(profileGateway);
  }

  @Test
  public void noProfileExistsException() {
    request.changeContractType("Tenured");
    interactor = new EditProfileInteractor(new InvalidProfileGatewaySpy());
    Response response = interactor.handle(request);
    assertEquals(ErrorResponse.unknownProfileName(), response);
  }

  @Test
  public void canTakeProfile() {
    request.changeContractType("Untenured");
    Response response = interactor.execute(request);
    assertEquals(new SuccessResponse(), response);
  }

  @Test
  public void canEditSingleField() {
    request.changeContractType("Untenured");
    interactor.execute(request);
    assertNotEquals("Tenured", providedProfile.getContract());
  }

  @Test
  public void canHandleMultipleChanges() {
    request.changeFullname("Bill Mill");
    request.changeDivision("Science");
    request.changeContractType("Sabbatical");
    interactor.execute(request);
    assertEquals("Bill Mill", providedProfile.getName());
    assertEquals("Sabbatical", providedProfile.getContract());
    assertEquals("Science", providedProfile.getDivision());
  }

  @Test
  public void canHandleBooleanChanges() {
    request.changeActiveStatus(false);
    interactor.execute(request);
    assertFalse(providedProfile.isActive());
  }

  @Test
  public void spyRemembersUsername() {
    request.changeActiveStatus("False");
    interactor.execute(request);
    assertFalse(providedProfile.isActive());
    Assert.assertEquals(request.username, profileGateway.providedUsername);
    Assert.assertTrue(ExistingProfileGatewaySpy.profileHasBeenEdited);
  }
}
