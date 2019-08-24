package fsc.request;

import java.util.List;

public class SubmitVoteRecordRequest extends Request {
  public final long voterId;
  public final String username;
  public final List<String> vote;

  public SubmitVoteRecordRequest(long voterId, String username, List<String> vote) {
    this.voterId = voterId;
    this.username = username;
    this.vote = vote;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}
