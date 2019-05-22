package fsc.response;

public class ErrorResponse extends Throwable implements Response {
  public static final String NO_PROFILE_FOUND = "No profile found!";
  public static final String NO_BALLOT_WITH_THAT_ID = "No ballot with that ID";
  public static final String NO_PROFILE_WITH_THAT_USERNAME = "No profile with that username";
  public String response;
  public ErrorResponse(String s) {
    response = s;
  }
}
