package webserver;

import fsc.app.AppContext;
import fsc.entity.Committee;
import fsc.entity.Election;
import fsc.gateway.Gateway;
import fsc.interactor.*;
import fsc.request.*;
import fsc.response.Response;
import fsc.response.ViewResponse;
import fsc.viewable.ViewableProfile;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class InteractionController {
  private ProfileInteractor profileInteractor;
  private final ContractTypeInteractor contractTypeInteractor;
  private final DivisionInteractor divisionInteractor;
  private final Gateway gateway;
  private final ElectionInteractor electionInteractor;

  InteractionController(Gateway gateway) {
    profileInteractor = new ProfileInteractor(gateway, gateway.getEntityFactory());
    contractTypeInteractor = new ContractTypeInteractor(gateway, gateway.getEntityFactory());
    divisionInteractor = new DivisionInteractor(gateway, gateway.getEntityFactory());
    electionInteractor = new ElectionInteractor(gateway, gateway, gateway,
                                                AppContext.getEntityFactory());
    this.gateway = gateway;
  }

  fsc.response.Response createProfile(Function<String, String> params) {
    String fullName = params.apply("fullName");
    String username = params.apply("username");
    String division = params.apply("division");
    String contractType = params.apply("contractType");
    CreateProfileRequest request = new CreateProfileRequest(fullName, username, division,
                                                            contractType);
    return profileInteractor.execute(request);
  }

  Collection<ViewableProfile> getAllProfiles() {
    Response response = profileInteractor.execute(new ViewProfilesListRequest());
    return ((ViewResponse<List<ViewableProfile>>) response).getValues();
  }

  public void setProfileInteractor(ProfileInteractor profileInteractor) {
    this.profileInteractor = profileInteractor;
  }

  public List<String> getAllContractTypes() {
    Response response = contractTypeInteractor.execute(new ViewContractsRequest());
    return ((ViewResponse<List<String>>) response).getValues();
  }

  public List<String> getAllDivisions() {
    Response response = divisionInteractor.execute(new ViewDivisionRequest());
    return ((ViewResponse<List<String>>) response).getValues();
  }

  public Collection<Committee> getAllCommittees() {
    return gateway.getCommittees();
  }

  public Response createElection(Function<String, String> params) {
    String committeeName = params.apply("committee");
    String seatName = params.apply("seat");
    CreateElectionRequest request = new CreateElectionRequest(seatName, committeeName);
    return electionInteractor.execute(request);
  }

  public Collection<Election> getAllElections() {
    return gateway.getAllElections();
  }
}
