package fixtures;

import fsc.viewable.ViewableCommittee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ViewVotes {

  public ViewVotes() { }

  public List<List<List<String>>> query() {
    return new ArrayList<>();
//    TestContext.app.getAllCommittees().stream()
//                                        .flatMap(ViewVotes::getListFromCommittee)
//                                        .collect(Collectors.toList());
  }

  private static Stream<List<List<String>>> getListFromCommittee(ViewableCommittee committee) {
    return committee.seats
                 .stream()
                 .map(s -> List.of(List.of("committee name", committee.name),
                                   List.of("seat name", s.name),
                                   List.of("seat query", s.query)));
  }
}
