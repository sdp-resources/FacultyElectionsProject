package fsc.viewable;

import java.util.List;

public class ViewableVoteRecord {
  public final String timestamp;
  public final String electionID;
  public final List<ViewableProfile> votes;

  public ViewableVoteRecord(String timestamp, String electionID, List<ViewableProfile> votes) {
    this.timestamp = timestamp;
    this.electionID = electionID;
    this.votes = votes;
  }
}
