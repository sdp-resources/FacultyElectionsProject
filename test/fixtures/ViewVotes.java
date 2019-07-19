package fixtures;

import fsc.viewable.ViewableVoteRecord;

import java.util.List;
import java.util.stream.Collectors;

public class ViewVotes {

  private Long electionId;

  public ViewVotes(Long electionId) {
    this.electionId = electionId;
  }

  public List<List<List<String>>> query() {
    return TestContext.app.getAllVotes(electionId)
                          .stream()
                          .map(this::getListFromRecord)
                          .collect(Collectors.toList());
  }

  private List<List<String>> getListFromRecord(ViewableVoteRecord record) {
    return List.of(List.of("votes", concatenateNames(record)));
  }

  private String concatenateNames(ViewableVoteRecord record) {
    return record.votes.stream()
                       .map(v -> v.username)
                       .collect(Collectors.joining(","));
  }
}
