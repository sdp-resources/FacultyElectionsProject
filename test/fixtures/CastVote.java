package fixtures;

import java.util.ArrayList;
import java.util.List;

public class CastVote {
  private final String voterName;
  private final String electionID;
  private List<String> vote = new ArrayList<>();

  public CastVote(String voterName, String electionID) {
    this.voterName = voterName;
    this.electionID = electionID;
  }

  public void addVoteFor(String candidate) {
    vote.add(candidate);
  }

  public boolean submit() {
    return TestContext.app.submitVote(voterName, electionID, vote);
  }
}

