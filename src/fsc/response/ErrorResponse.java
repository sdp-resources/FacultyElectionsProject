package fsc.response;

import java.util.Objects;

public class ErrorResponse extends Throwable implements Response {
  public static final String NO_BALLOT_WITH_THAT_ID = "No ballot with that ID";
  public static final String NO_PROFILE_WITH_THAT_USERNAME = "Unknown username";
  public static final String UNKNOWN_COMMITTEE_NAME = "Unknown Committee Name";
  public static final String UNKNOWN_SEAT_NAME = "Unknown Seat Name";
  public static final String UNKNOWN_ELECTION_ID = "Unknown Election ID";
  public static final String RESOURCE_EXISTS = "Resource already exists";
  public static final String NOT_AUTHORIZED = "Not Authorized";
  public static final String INVALID_CANDIDATE = "Ballot does not contain profile";
  public static final String NO_HANDLERS = "No available handler for that request";
  public String message;

  public ErrorResponse(String s) {
    message = s;
  }

  public static ErrorResponse unknownCommitteeName() {
    return new ErrorResponse(UNKNOWN_COMMITTEE_NAME);
  }

  public static ErrorResponse unknownSeatName() {
    return new ErrorResponse(UNKNOWN_SEAT_NAME);
  }

  public static ErrorResponse unknownElectionID() {
    return new ErrorResponse(UNKNOWN_ELECTION_ID);
  }

  public static ErrorResponse unknownProfileName() {
    return new ErrorResponse(NO_PROFILE_WITH_THAT_USERNAME);
  }

  public static ErrorResponse unknownBallotID() {
    return new ErrorResponse(NO_BALLOT_WITH_THAT_ID);
  }

  public static ErrorResponse resourceExists() {
    return new ErrorResponse(RESOURCE_EXISTS);
  }

  public static ErrorResponse notAuthorized() {
    return new ErrorResponse(NOT_AUTHORIZED);
  }

  public static ErrorResponse invalidCandidate() {
    return new ErrorResponse(INVALID_CANDIDATE);
  }

  public static ErrorResponse cannotHandle() {
    return new ErrorResponse(NO_HANDLERS);
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return Objects.equals(message, ((ErrorResponse) o).message);
  }

  public int hashCode() {
    return Objects.hash(message);
  }

  public String toString() {
    return "ErrorResponse: \'" + message + '\'';
  }
}
