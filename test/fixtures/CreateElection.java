package fixtures;

public class CreateElection {
  private String electionId;

  public CreateElection(String committeeName, String seatName) {
    electionId = TestContext.app.createElection(committeeName, seatName);
  }

  public boolean electionIdIsNotNull() {
    return electionId != null;
  }

  public String getElectionId() {
    return electionId;
  }

  public boolean addCandidate(String name) {
    return TestContext.app.addCandidate(electionId, name);
  }

  public boolean createVoter(String username, String electionId) {
    // TODO: Need to be able to add voters
    return false;
  }
}

