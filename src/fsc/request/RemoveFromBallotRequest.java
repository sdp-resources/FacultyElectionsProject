package fsc.request;

import fsc.response.Response;

public class RemoveFromBallotRequest extends Request {
  private final String profileUsername;
  private final String ballotID;

  public RemoveFromBallotRequest(String ballotID, String profileUsername) {
    this.ballotID = ballotID;
    this.profileUsername = profileUsername;
  }

  public String getBallotID() {return ballotID;}

  public String getProfileUsername(){
    return profileUsername;
  }
}
