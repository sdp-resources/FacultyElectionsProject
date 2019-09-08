package fsc.response;

import java.util.Objects;

public class ErrorResponse implements Response {
  public static final String NO_PROFILE_WITH_THAT_USERNAME = "Unknown username";
  public static final String UNKNOWN_COMMITTEE_NAME = "Unknown Committee Name";
  public static final String UNKNOWN_SEAT_NAME = "Unknown Seat Name";
  public static final String UNKNOWN_ELECTION_ID = "Unknown Election ID";
  public static final String RESOURCE_EXISTS = "Resource already exists";
  public static final String NOT_AUTHORIZED = "Not Authorized";
  public static final String INVALID_CANDIDATE = "Ballot does not contain profile";
  public static final String NO_HANDLERS = "No available handler for that request";
  public static final String VOTER_ALREADY_VOTED = "Voter has already voted on this election.";
  public static final String MULTIPLE_RANKS_FOR_CANDIDATE = "Multiple rankings for the same candidate";
  public static final String NO_VOTE_RECORDED = "No vote has been recorded for that username";
  public static final String INVALID_ELECTION_STATE = "This is not a valid election state";
  public static final String INVALID_VOTER = "You are not eligible to vote on this election";
  public static final String VOTER_EXISTS = "This voter already exists";
  public static final String IMPROPER_ELECTION_STATE = "The operation cannot be performed on " +
                                                             "this election state";
  public static final String INVALID_CREDENTIALS = "The username and password are invalid";
  public static final String INVALID_SESSION = "Session token no longer valid";
  public static final String INVALID_CANDIDATE_STATE = "Not a valid candidate state";
  public static final String INVALID_QUERY_NAME = "Unknown query name";
  public String message;

  public ErrorResponse(String s) {
    message = s;
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

  public boolean isSuccessful() {
    return false;
  }
}
