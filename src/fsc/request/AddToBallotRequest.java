package fsc.request;

import fsc.entity.Ballot;
import fsc.entity.Profile;

public class AddToBallotRequest {
  private final Profile profile;
  private final Ballot ballot;

  public AddToBallotRequest(Ballot ballot, Profile profile) {
    this.ballot = ballot;
    this.profile = profile;
  }

  public Ballot getBallot() {return ballot;}

  public Profile getProfile(){
    return profile;
  }
}
