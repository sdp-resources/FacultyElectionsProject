package fixtures;

import java.util.HashMap;
import java.util.Map;

public class ProfileEdit {
  private Map<String, Object> changes = new HashMap<>();
  private String username;

  public ProfileEdit(String username) {
    this.username = username;
  }

  public void setFullName(String name) {
    changes.put("fullname", name);
  }

  public void setDivision(String division) {
    changes.put("division", division);
  }

  public void setContractType(String contractType) {
    changes.put("contractType", contractType);
  }

  public boolean sendRequest() {
    return TestContext.editProfile(username, changes);
  }
}

