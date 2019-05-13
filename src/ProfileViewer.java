public class ProfileViewer {
  public String name;
  public boolean isActive;

  public ProfileViewer(Profile profile){
    name = profile.getName();
    isActive = determineIfProfileIsActive(profile.contract);
  }

  private boolean determineIfProfileIsActive(String status) {
    switch (status) {
      case "inactive":
        return false;
      case "sabbatical":
        return false;
      case "tenure":
        return true;
    }
    return false;
  }
}
