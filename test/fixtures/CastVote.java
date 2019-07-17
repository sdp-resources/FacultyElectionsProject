package fixtures;

import java.util.ArrayList;
import java.util.List;

public class CastVote {
  private final String voterName;
  private final String electionID;
  private List<String> vote = new ArrayList<>();
  private long voterId;

  public CastVote(String voterId, String voterName, String electionID) {
    this.voterId = Long.valueOf(voterId);
    this.voterName = voterName;
    this.electionID = electionID;
  }

  public void addVoteFor(String candidate) {
    vote.add(candidate);
  }

  public boolean submit() {
    return TestContext.app.submitVote(voterId, vote);
  }
}

