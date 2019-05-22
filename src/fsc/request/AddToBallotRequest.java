package fsc.request;

public class AddToBallotRequest extends Request {
  private final String profileUsername;
  private final String ballotID;

  public AddToBallotRequest(String ballotID, String profileUsername) {
    this.ballotID = ballotID;
    this.profileUsername = profileUsername;
  }

  public String getBallotID() {return ballotID;}

  public String getProfileUsername(){
    return profileUsername;
  }
}
