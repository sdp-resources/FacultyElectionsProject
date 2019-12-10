package fsc.entity;

import java.util.Objects;

public class Profile {
  private String name;
  private String username;
  private String division;
  private String contract;
  private Status status;

  public Profile() {}

  public enum Status {
    ACTIVE {
      public Boolean isActive() { return true; }

      public String toString() { return "active"; }
    }, INACTIVE {
      public Boolean isActive() { return false; }

      public String toString() { return "inactive"; }
    };

    public Boolean isActive() { return null; }
  }

  public Profile(String name, String username, String division, String contract) {
    this.name = name;
    this.division = division;
    this.username = username;
    this.contract = contract;
    this.status = Status.ACTIVE;
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

  public Status getStatus() { return status; }

  public Boolean isActive() { return status.isActive(); }

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

  public void setStatus(Status status) {
    this.status = status;
  }

  public void setInactive() {
    setStatus(Status.INACTIVE);
  }

  public void setActive() {
    setStatus(Status.ACTIVE);
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Profile profile = (Profile) o;
    return username.equals(profile.username);
  }

  public int hashCode() {
    return Objects.hash(username);
  }

  public String toString() {
    return "Profile{" + username + '}';
  }
}
