public class ProfileViewer {
  public String name;
  public boolean status;
  public String department;

  public ProfileViewer(Profile profile){
    department = profile.getDepartment();
    name = profile.getName();
    status = determineIfProfileIsActive(profile.contract);
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
