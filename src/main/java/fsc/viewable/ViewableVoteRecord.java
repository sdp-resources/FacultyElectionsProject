package fsc.viewable;

import java.util.List;
import java.util.Objects;

public class ViewableVoteRecord {
  public final Long recordId;
  public final String timestamp;
  public final List<String> votes;

  public ViewableVoteRecord(String timestamp, Long recordId, List<String> votes) {
    this.timestamp = timestamp;
    this.recordId = recordId;
    this.votes = votes;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewableVoteRecord that = (ViewableVoteRecord) o;
    return timestamp.equals(that.timestamp) &&
                 recordId.equals(that.recordId) &&
                 votes.equals(that.votes);
  }

  public int hashCode() {
    return Objects.hash(timestamp, recordId, votes);
  }
}
