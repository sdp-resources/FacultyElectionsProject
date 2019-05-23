package fsc.response;

import java.util.Objects;

public class ErrorResponse extends Throwable implements Response {
  public static final String NO_PROFILE_FOUND = "No profile found!";
  public static final String NO_BALLOT_WITH_THAT_ID = "No ballot with that ID";
  public static final String NO_PROFILE_WITH_THAT_USERNAME = "No profile with that username";
  public static final String INVALID_COMMITTEE_NAME = "Invalid Committee Name";
  public static final String UNKNOWN_SEAT_NAME = "Unknown Seat Name";
  public static final String UNKNOWN_ELECTION_ID = "Unknown Election ID";
  public String message;

  public ErrorResponse(String s) {
    message = s;
  }

  public static ErrorResponse invalidCommitteeName() {
    return new ErrorResponse(INVALID_COMMITTEE_NAME);
  }

  public static ErrorResponse unknownSeatName() {
    return new ErrorResponse(UNKNOWN_SEAT_NAME);
  }

  public static ErrorResponse unknownElectionID() {
    return new ErrorResponse(UNKNOWN_ELECTION_ID);
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return Objects.equals(message, ((ErrorResponse) o).message);
  }

  public int hashCode() {
    return Objects.hash(message);
  }
}
