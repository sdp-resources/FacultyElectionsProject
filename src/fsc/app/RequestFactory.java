package fsc.app;

import fsc.request.*;

import java.util.Map;

public class RequestFactory {
  public RequestFactory() { }

  Request viewProfilesList(String query) {
    return new ViewProfilesListRequest(query);
  }

  Request createProfile(
        String fullname, String username, String contractType, String division
  ) {
    return new CreateProfileRequest(fullname, username, division, contractType);
  }

  Request viewProfile(String username) {
    return new ViewProfileRequest(username);
  }

  public Request editProfile(String username, Map<String, String> changes) {
    return new EditProfileRequest(username, changes);
  }

  Request addDivision(String division) {
    return new AddDivisionRequest(division);
  }

  Request addContractType(String contractType) {
    return new AddContractTypeRequest(contractType);
  }

  Request viewDivisionList() {
    return new ViewDivisionRequest();
  }

  Request viewContractTypeList() {
    return new ViewContractsRequest();
  }
}