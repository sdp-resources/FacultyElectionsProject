package fixtures;

import fsc.request.EditSeatRequest;

import java.util.HashMap;
import java.util.Map;

public class SeatEdit {
  private String committeeName;
  private String seatName;
  private Map<String, String> changes = new HashMap<>();

  public SeatEdit(String committeeName, String seatName) {
    this.committeeName = committeeName;
    this.seatName = seatName;
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
    return TestContext.app.editSeat(committeeName, seatName, changes);
  }
}

