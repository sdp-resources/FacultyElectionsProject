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

  public long createVoter(String username, String electionId) {
    return TestContext.app.addVoter(username, electionId).voterId;
  }
}

