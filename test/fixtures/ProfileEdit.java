package fixtures;

import fsc.request.EditProfileRequest;

public class ProfileEdit {
  private EditProfileRequest request;

  public ProfileEdit(String username) {
    request = new EditProfileRequest(username);
  }

  public void setFullName(String name) {
    request.changeFullname(name);
  }

  public void setDivision(String division) {
    request.changeDivision(division);
  }

  public void setContractType(String contractType) {
    request.changeContractType(contractType);
  }

  public boolean sendRequest() {
    return TestContext.editProfile(request);
  }
}

