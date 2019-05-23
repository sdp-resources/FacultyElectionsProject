package webserver;

import fsc.interactor.*;
import fsc.request.*;
import fsc.response.*;
import fsc.viewable.ViewableProfile;
import gateway.InMemoryGateway;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class InteractionController {
  private ViewProfilesListInteractor viewProfilesListInteractor;
  private CreateProfileInteractor createProfileInteractor;
  private ViewContractsInteractor viewContractsInteractor;
  private ViewDivisionInteractor viewDivisionsInteractor;

  public InteractionController(InMemoryGateway gateway) {
    viewProfilesListInteractor = new ViewProfilesListInteractor(gateway);
    createProfileInteractor = new CreateProfileInteractor(gateway);
    viewContractsInteractor = new ViewContractsInteractor(gateway);
    viewDivisionsInteractor = new ViewDivisionInteractor(gateway);
  }

  fsc.response.Response createProfile(Function<String, String> params) {
    String fullName = params.apply("fullName");
    String username = params.apply("username");
    String division = params.apply("division");
    String contractType = params.apply("contractType");
    CreateProfileRequest request = new CreateProfileRequest(fullName, username, division,
                                                            contractType);
    return createProfileInteractor.execute(request);
  }

  Collection<ViewableProfile> getAllProfiles() {
    ViewProfilesListRequest request = new ViewProfilesListRequest();
    fsc.response.Response response = viewProfilesListInteractor.execute(request);
    return ((ViewProfilesListResponse) response).viewableProfiles;
  }

  public void setViewProfilesListInteractor(
        ViewProfilesListInteractor viewProfilesListInteractor
  ) {
    this.viewProfilesListInteractor = viewProfilesListInteractor;
  }

  public void setCreateProfileInteractor(CreateProfileInteractor createProfileInteractor) {
    this.createProfileInteractor = createProfileInteractor;
  }

  public List<String> getAllContractTypes() {
    ViewContractsResponse response = viewContractsInteractor.execute(new ContractsViewerRequest());
    return response.contracts;
  }

  public List<String> getAllDivisions() {
    Response response = viewDivisionsInteractor.execute(new ViewDivisionRequest());
    return ((ViewDivisionResponse) response).divisions;
  }
}
