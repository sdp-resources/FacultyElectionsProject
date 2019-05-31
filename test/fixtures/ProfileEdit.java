package fixtures;

import java.util.HashMap;
import java.util.Map;

public class ProfileEdit {
  private String username;
  private Map<String, String> changes = new HashMap<>();

  public ProfileEdit(String username) {
    this.username = username;
  }

  public void setFullName(String name) {
    changes.put("name", name);
  }

  public void setDivision(String division) {
    changes.put("division", division);
  }

  public void setContractType(String contractType) {
    changes.put("contractType", contractType);
  }

  public void setStatus(String status) {
    changes.put("status", status);
  }

  public boolean sendRequest() {
    return TestContext.app.editProfile(username, changes);
  }
}

