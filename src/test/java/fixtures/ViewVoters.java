package fixtures;

import fsc.viewable.ViewableVoter;

import java.util.List;
import java.util.stream.Collectors;

public class ViewVoters {

  private Long electionId;

  public ViewVoters(Long electionId) {
    this.electionId = electionId;
  }

  public List<List<List<String>>> query() {
    return TestContext.app.viewElection(electionId)
                          .voters
                          .stream()
                          .map(this::getListFromVoter)
                          .collect(Collectors.toList());
  }

  private List<List<String>> getListFromVoter(ViewableVoter voter) {
    return List.of(List.of("name", voter.profile.getUsername()),
                   List.of("electionId", String.valueOf(voter.electionId)),
                   List.of("voted", String.valueOf(voter.voted)),
                   List.of("voterId", String.valueOf(voter.voterId)));
  }

}
