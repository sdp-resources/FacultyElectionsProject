package fixtures;

import fsc.viewable.ViewableProfile;
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
                           .map(this::getListFromProfile)
                           .collect(Collectors.toList());
  }

  private List<List<String>> getListFromProfile(ViewableProfile profile) {
    return List.of(List.of("name", profile.getUsername()));
  }
}
