package webserver;

import fsc.entity.Committee;
import fsc.entity.Election;
import fsc.interactor.*;
import fsc.request.*;
import fsc.response.Response;
import fsc.viewable.ViewableProfile;
import gateway.InMemoryGateway;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class InteractionController {
  private ViewProfilesListInteractor viewProfilesListInteractor;
  private CreateProfileInteractor createProfileInteractor;
  private final ViewContractsInteractor viewContractsInteractor;
  private final ViewDivisionInteractor viewDivisionsInteractor;
  private final InMemoryGateway gateway;
  private final CreateElectionInteractor createElectionInteractor;

  InteractionController(InMemoryGateway gateway) {
    viewProfilesListInteractor = new ViewProfilesListInteractor(gateway);
    createProfileInteractor = new CreateProfileInteractor(gateway);
    viewContractsInteractor = new ViewContractsInteractor(gateway);
    viewDivisionsInteractor = new ViewDivisionInteractor(gateway);
    createElectionInteractor = new CreateElectionInteractor(gateway, gateway, gateway);
    this.gateway = gateway;
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
    return viewProfilesListInteractor.execute(new ViewProfilesListRequest()).values;
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
    return viewContractsInteractor.execute(new ContractsViewerRequest()).values;
  }

  public List<String> getAllDivisions() {
    return viewDivisionsInteractor.execute(new ViewDivisionRequest()).values;
  }

  public Collection<Committee> getAllCommittees() {
    return gateway.getAllCommittees();
  }

  public Response createElection(Function<String, String> params) {
    String committeeName = params.apply("committee");
    String seatName = params.apply("seat");
    CreateElectionRequest request = new CreateElectionRequest(seatName, committeeName);
    return createElectionInteractor.execute(request);
  }

  public Collection<Election> getAllElections() {
    return gateway.getAllElections();
  }
}
