package fsc.request;

public class AddToBallotRequest extends Request {
  private final String profileUsername;
  private final String electionId;

  public AddToBallotRequest(String electionID, String profileUsername) {
    this.electionId = electionID;
    this.profileUsername = profileUsername;
  }

  public String getElectionId() {return electionId;}

  public String getProfileUsername() {
    return profileUsername;
  }
}
