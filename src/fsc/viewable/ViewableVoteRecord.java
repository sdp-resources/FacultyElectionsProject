package fsc.viewable;

import java.util.List;

public class ViewableVoteRecord {
  public final ViewableProfile voter;
  public final String timestamp;
  public final String electionID;
  public final List<ViewableProfile> votes;

  public ViewableVoteRecord(
        ViewableProfile voter, String timestamp, String electionID,
        List<ViewableProfile> votes
  ) {
    this.voter = voter;
    this.timestamp = timestamp;
    this.electionID = electionID;
    this.votes = votes;
  }
}
