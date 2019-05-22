package fsc.request;

import java.util.Map;

public class EditProfileRequest extends Request {

  public final String username;
  public final Map<String, Object> changes;

  public EditProfileRequest(String username, Map<String,Object> changes) {
    this.username = username;
    this.changes = changes;
  }

}
