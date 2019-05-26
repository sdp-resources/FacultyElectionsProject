package fsc.entity;

public class Profile {
  private String name;
  public String username;
  private String division;
  private String contract;
  private Boolean active;

  public Profile(String name, String username, String division, String contract) {
    this.name = name;
    this.division = division;
    this.username = username;
    this.contract = contract;
    this.active = true;
  }

  public String getName() {
    return name;
  }

  public String getDivision() {
    return division;
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

  public void setDivision(String newDepartment) {
    division = newDepartment;
  }

  public void setUsername(String newUsername) {
    username = newUsername;
  }

  public void setInactive() { active = false; }

  public void setActive() { active = true; }
}
