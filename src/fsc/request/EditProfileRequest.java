package fsc.request;

import java.util.Map;

public class EditProfileRequest {

  public final String username;
  public final Map<String, String> changes;

  public EditProfileRequest(String username, Map<String,String> changes) {
    this.username = username;
    this.changes = changes;
  }

}
