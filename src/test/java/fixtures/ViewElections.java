package fixtures;

import fsc.viewable.ViewableElection;

import java.util.List;
import java.util.stream.Collectors;

public class ViewElections {
  public List<List<List<String>>> query() {
    return TestContext.app.getAllElections()
                          .stream()
                          .map(this::getListFromElection)
                          .collect(Collectors.toList());
  }

  private List<List<String>> getListFromElection(ViewableElection election) {
    return List.of(List.of("seat name", election.seat.name),
                   List.of("state", election.state),
                   List.of("number of candidates", String.valueOf(election.candidates.size())),
                   List.of("number of voters", String.valueOf(election.voters.size())),
                   List.of("number of votes", String.valueOf(election.votes.size())),
                   List.of("electionId", String.valueOf(election.electionID))
          );

  }

}
