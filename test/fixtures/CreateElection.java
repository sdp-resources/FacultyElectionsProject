package fixtures;

public class CreateElection {
  private long electionId;

  public CreateElection(String committeeName, String seatName) {
    electionId = TestContext.app.createElection(committeeName, seatName).electionID;
  }

  public long getElectionId() {
    return electionId;
  }

  public boolean addCandidate(String name) {
    return TestContext.app.addCandidate(electionId, name);
  }

  public long createVoter(String username, Long electionId) {
    return TestContext.app.addVoter(username, electionId).voterId;
  }

  public boolean setElectionState(Long electionId, String state) {
    return TestContext.app.setElectionState(electionId, state).isSuccessful();
  }
}

