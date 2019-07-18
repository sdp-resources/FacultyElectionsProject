package fixtures;

import java.util.ArrayList;
import java.util.List;

public class CastVote {
  private List<String> vote = new ArrayList<>();
  private long voterId;

  public CastVote(String voterId) {
    this.voterId = Long.valueOf(voterId);
  }

  public void addVoteFor(String candidate) {
    vote.add(candidate);
  }

  public long submit() {
    return TestContext.app.submitVote(voterId, vote).recordId;
  }
}

