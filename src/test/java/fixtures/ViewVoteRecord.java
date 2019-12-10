package fixtures;

import fsc.viewable.ViewableVoteRecord;

import java.util.List;
import java.util.stream.Collectors;

public class ViewVoteRecord {

  private ViewableVoteRecord voteRecord;

  public ViewVoteRecord(long recordId) {
    voteRecord = TestContext.app.getVoteRecord(recordId);
  }

  public List<List<List<String>>> query() {
    return voteRecord.votes.stream()
                           .map(s -> List.of(List.of("name", s)))
                           .collect(Collectors.toList());
  }
}
