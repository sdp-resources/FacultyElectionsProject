package fsc.entity;

public class Profile {
  String name;
  public String username;
  String department;
  String contract;
  Boolean active;

  public Profile(String name, String username, String department, String contract) {
    this.name = name;
    this.department = department;
    this.username = username;
    this.contract = contract;
    this.active = true;
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

  public String getUsername() {
    return username;
  }

  public Boolean isActive() {return active;}

  public void setContract(String contractType) {
    contract = contractType;
  }

  public void setName(String newName) {
    name = newName;
  }

  public void setDepartment(String newDepartment) {
    department = newDepartment;
  }

  public void setUsername(String newUsername) {
    username = newUsername;
  }
}
