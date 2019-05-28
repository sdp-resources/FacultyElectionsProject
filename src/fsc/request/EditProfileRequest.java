package fsc.request;

import java.util.Map;

public class EditProfileRequest extends Request {
  public static final String CHANGE_CONTRACT_TYPE = "contractType";
  public static final String CHANGE_NAME = "fullname";
  public static final String CHANGE_DIVISION = "division";
  public static final String CHANGE_ACTIVE = "active";
  public final String username;
  public final Map<String, Object> changes;

  public EditProfileRequest(String username, Map<String, Object> changes) {
    this.username = username;
    this.changes = changes;
  }

}
