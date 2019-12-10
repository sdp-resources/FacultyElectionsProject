package fsc.request;

import java.util.Map;

public class EditSeatRequest extends Request {
  public static final String EDIT_SEAT_PROFILE = "profile";
  public static final String EDIT_SEAT_NAME = "name";
  public static final String EDIT_SEAT_QUERY = "query";
  public final long seatId;
  public final Map<String, String> changes;

  public EditSeatRequest(long seatId, Map<String, String> changes) {
    this.seatId = seatId;
    this.changes = changes;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}
