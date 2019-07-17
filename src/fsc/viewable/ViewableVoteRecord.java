package fsc.viewable;

import java.util.List;
import java.util.Objects;

public class ViewableVoteRecord {
  public final String timestamp;
  public final String electionID;
  public final List<ViewableProfile> votes;

  public ViewableVoteRecord(String timestamp, String electionID, List<ViewableProfile> votes) {
    this.timestamp = timestamp;
    this.electionID = electionID;
    this.votes = votes;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewableVoteRecord that = (ViewableVoteRecord) o;
    return timestamp.equals(that.timestamp) &&
                 electionID.equals(that.electionID) &&
                 votes.equals(that.votes);
  }

  public int hashCode() {
    return Objects.hash(timestamp, electionID, votes);
  }
}
