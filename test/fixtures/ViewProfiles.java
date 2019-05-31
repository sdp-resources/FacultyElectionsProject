package fixtures;

import fsc.viewable.ViewableProfile;

import java.util.List;
import java.util.stream.Collectors;

public class ViewProfiles {
  private String which;

  public ViewProfiles(String which) {
    this.which = which;
  }

  public List<List<List<String>>> query() {
    return TestContext.getProfilesForQuery(which).stream()
                      .map(ViewProfiles::handleProfile)
                      .collect(Collectors.toList());
  }

  private static List<List<String>> handleProfile(ViewableProfile profile) {
    return List.of(List.of("fullname", profile.name), List.of("username", profile.username),
                   List.of("division", profile.division),
                   List.of("contract type", profile.contract));
  }
}
