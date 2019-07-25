package fsc.request;

import fsc.interactor.Interactor;

import java.util.List;

public class SubmitVoteRecordRequest extends Request {
  public final long voterId;
  public final List<String> vote;

  public SubmitVoteRecordRequest(long voterId, List<String> vote) {
    this.voterId = voterId;
    this.vote = vote;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}
