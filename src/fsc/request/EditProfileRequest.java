package fsc.request;

import java.util.HashMap;
import java.util.Map;

public class EditProfileRequest {

  private final String username;
  private final Map<String, String> changes;

  public EditProfileRequest(String username, Map<String,String> changes) {
    this.username = username;
    this.changes = changes;
  }

}
