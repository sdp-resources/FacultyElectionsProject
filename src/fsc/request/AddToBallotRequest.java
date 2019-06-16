package fsc.request;

public class AddToBallotRequest extends Request {
  private final String profileUsername;
  private final String ballotID;

  public AddToBallotRequest(String electionID, String profileUsername) {
    this.ballotID = electionID;
    this.profileUsername = profileUsername;
  }

  public String getBallotID() {return ballotID;}

  public String getProfileUsername() {
    return profileUsername;
  }
}
