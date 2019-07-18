package fsc.request;

public class AddVoterRequest extends Request {
  public final String username;
  public final String electionId;

  public AddVoterRequest(String username, String electionId) {
    super();
    this.username = username;
    this.electionId = electionId;
  }
}
