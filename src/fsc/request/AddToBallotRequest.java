package fsc.request;

import fsc.entity.Profile;

public class AddToBallotRequest {
  private final String ballotID;
  private final Profile profile;

  public AddToBallotRequest(String ballotID, Profile profile) {
    this.ballotID = ballotID;
    this.profile = profile;
  }

  public String getBallotID() {
    return ballotID;
  }

  public Profile getProfile(){
    return profile;
  }
}
