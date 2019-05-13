
public class Profile {
  String name;
  String department;
  String contract;

  public Profile(String name, String department) {
    this.name = name;
    this.department = department;
  }

  public String getName() {
    return name;
  }

  public String getDepartment() {
    return department;
  }

  public String getContract() {
    return contract;
  }

  public void setContract(String contractType) {
    contract = contractType;
  }
}
