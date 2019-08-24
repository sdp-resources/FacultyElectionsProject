package fixtures;

import java.util.ArrayList;
import java.util.List;

public class CastVote {
  private List<String> vote = new ArrayList<>();
  private long voterId;
  private String username;

  public CastVote(String voterId, String username) {
    this.voterId = Long.valueOf(voterId);
    this.username = username;
  }

  public void addVoteFor(String candidate) {
    vote.add(candidate);
  }

  public long submit() {
    return TestContext.app.submitVote(voterId, username, vote).recordId;
  }
}

