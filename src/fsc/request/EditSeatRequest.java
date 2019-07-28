package fsc.request;

import java.util.Map;

public class EditSeatRequest extends Request {
  public static final String EDIT_SEAT_PROFILE = "profile";
  public static final String EDIT_SEAT_NAME = "name";
  public static final String EDIT_SEAT_QUERY = "query";
  public final String committeeName;
  public final String seatName;
  public final Map<String, String> changes;

  public EditSeatRequest(
        String committeeName, String seatName, Map<String, String> changes
  ) {
    this.committeeName = committeeName;
    this.seatName = seatName;
    this.changes = changes;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}
