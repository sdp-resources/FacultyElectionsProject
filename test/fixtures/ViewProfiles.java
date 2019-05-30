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
    List<ViewableProfile> profiles = getProfiles();
    return profiles.stream().map(ViewProfiles::handleProfile).collect(Collectors.toList());
  }

  private List<ViewableProfile> getProfiles() {
    switch (which) {
      case "all": return TestContext.getAllProfiles();
      case "active": return TestContext.getActiveProfiles();
      default: throw new RuntimeException("Unknown setting for 'which'");
    }
  }

  private static List<List<String>> handleProfile(ViewableProfile profile) {
    return List.of(List.of("fullname", profile.name), List.of("username", profile.username),
                   List.of("division", profile.division),
                   List.of("contract type", profile.contract));
  }
}
