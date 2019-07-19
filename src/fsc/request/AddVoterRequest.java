package fsc.request;

public class AddVoterRequest extends Request {
  public final String username;
  public final Long electionId;

  public AddVoterRequest(String username, Long electionId) {
    super();
    this.username = username;
    this.electionId = electionId;
  }
}
