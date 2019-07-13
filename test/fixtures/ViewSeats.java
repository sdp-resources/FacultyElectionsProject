package fixtures;

import fsc.viewable.ViewableCommittee;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewSeats {

  public ViewSeats() { }

  public List<List<List<String>>> query() {
    return TestContext.app.getAllCommittees().stream()
                          .flatMap(ViewSeats::getListFromCommittee)
                          .collect(Collectors.toList());
  }

  private static Stream<List<List<String>>> getListFromCommittee(ViewableCommittee committee) {
    return committee.seats
                 .stream()
                 .map(s -> List.of(List.of("seat id",  s.id),
                                   List.of("committee name", committee.name),
                                   List.of("seat name", s.name),
                                   List.of("seat query", s.query)));
  }
}
