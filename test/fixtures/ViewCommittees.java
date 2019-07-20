package fixtures;

import fsc.viewable.ViewableCommittee;

import java.util.List;
import java.util.stream.Collectors;

public class ViewCommittees {

  public ViewCommittees() { }

  public List<List<List<String>>> query() {
    return TestContext.app.getAllCommittees().stream()
                          .map(ViewCommittees::getListFromCommittee)
                          .collect(Collectors.toList());
  }

  private static List<List<String>> getListFromCommittee(ViewableCommittee committee) {
    return List.of(List.of("name", committee.name),
                   List.of("description", committee.description),
                   List.of("voter query", committee.voterQuery),
                   List.of("number of seats", String.valueOf(committee.seats.size())));
  }
}
