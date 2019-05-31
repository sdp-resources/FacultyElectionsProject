package fsc.app;

import fsc.gateway.Gateway;
import fsc.interactor.*;
import fsc.request.Request;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import fsc.response.ViewResponse;
import fsc.viewable.ViewableProfile;

import java.util.List;
import java.util.Map;

public class AppContext {
  private RequestFactory requestFactory = new RequestFactory();
  public Gateway gateway;
  public Interactor interactor;

  public AppContext(Gateway gateway) {
    this.gateway = gateway;
    this.interactor = loadInteractors(gateway);
  }

  public Interactor loadInteractors(Gateway gateway) {
    return new ViewDivisionInteractor(gateway)
                 .append(new AddDivisionInteractor(gateway))
                 .append(new ViewContractsInteractor(gateway))
                 .append(new AddContractTypeInteractor(gateway))
                 .append(new CreateProfileInteractor(gateway))
                 .append(new ViewProfileInteractor(gateway))
                 .append(new EditProfileInteractor(gateway))
                 .append(new ViewProfilesListInteractor(gateway));
  }

  public List<ViewableProfile> getProfilesForQuery(String query) {
    return getViewableProfileListResult(requestFactory.viewProfilesList(query));
  }

  public boolean editProfile(String username, Map<String, String> changes) {
    return isSuccessful(requestFactory.editProfile(username, changes));
  }

  public boolean addProfile(
        String fullname, String username,
        String contractType, String division
  ) {
    return isSuccessful(requestFactory.createProfile(fullname, username, contractType, division));
  }

  public ViewableProfile getProfile(String username) {
    return getViewableProfileResult(requestFactory.viewProfile(username));
  }

  public boolean addDivision(String division) {
    return isSuccessful(requestFactory.addDivision(division));
  }

  public boolean addContractType(String contractType) {
    return isSuccessful(requestFactory.addContractType(contractType));
  }

  public boolean hasDivision(String division) {
    return getStringListResult(requestFactory.viewDivisionList()).contains(division);
  }

  public boolean hasContractType(String contractType) {
    return getStringListResult(requestFactory.viewContractTypeList()).contains(contractType);
  }

  private boolean isSuccessful(Request request) {
    Response response = getResponse(request);
    return isResponseSuccessful(response);
  }

  private Response getResponse(Request request) {
    return interactor.handle(request);
  }

  private boolean isResponseSuccessful(Response response) {
    return response.equals(new SuccessResponse());
  }

  private List<String> getStringListResult(Request request) {
    Response response = getResponse(request);
    return ((ViewResponse<List<String>>) response).values;
  }

  private ViewableProfile getViewableProfileResult(Request request) {
    Response response = getResponse(request);
    return ((ViewResponse<ViewableProfile>) response).values;
  }

  private List<ViewableProfile> getViewableProfileListResult(Request request) {
    Response response = getResponse(request);
    return ((ViewResponse<List<ViewableProfile>>) response).values;
  }
}