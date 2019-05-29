package fixtures;

import fsc.viewable.ViewableProfile;

import java.util.List;
import java.util.stream.Collectors;

public class ViewAllProfiles {

  public List<List<List<String>>> query() {
    List<ViewableProfile> profiles = TestContext.getAllProfiles();
    return profiles.stream().map(ViewAllProfiles::handleProfile).collect(Collectors.toList());
  }

  private static List<List<String>> handleProfile(ViewableProfile profile) {
    return List.of(List.of("fullname", profile.name), List.of("username", profile.username),
                   List.of("division", profile.division),
                   List.of("contract type", profile.contract));
  }
}
