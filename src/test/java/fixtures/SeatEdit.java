package fixtures;

import fsc.request.EditSeatRequest;

import java.util.HashMap;
import java.util.Map;

public class SeatEdit {
  private Map<String, String> changes = new HashMap<>();
  private long seatId;

  public SeatEdit(long seatId) {
    this.seatId = seatId;
  }

  public void setName(String name) {
    changes.put(EditSeatRequest.EDIT_SEAT_NAME, name);
  }

  public void setProfile(String profile) {
    changes.put(EditSeatRequest.EDIT_SEAT_PROFILE, profile);
  }

  public void setQuery(String query) {
    changes.put(EditSeatRequest.EDIT_SEAT_QUERY, query);
  }

  public boolean sendRequest() {
    return TestContext.app.editSeat(seatId, changes);
  }
}

