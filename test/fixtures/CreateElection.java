package fixtures;

public class CreateElection {
  private String electionId;

  public CreateElection(String committeeName, String seatName) {
    electionId = TestContext.app.createElection(committeeName, seatName);
  }

  public boolean addCandidate(String name) {
    return false;
  }
}

