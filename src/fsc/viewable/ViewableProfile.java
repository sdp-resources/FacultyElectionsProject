package fsc.viewable;

import java.util.Objects;

public class ViewableProfile {
  public final String name;
  public final String username;
  public final String division;
  public final String contract;

  public ViewableProfile(String name, String username, String division, String contract) {
    this.name = name;
    this.username = username;
    this.division = division;
    this.contract = contract;
  }

  public String getName() { return name; }

  public String getUsername() { return username; }

  public String getDivision() { return division; }

  public String getContract() { return contract; }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewableProfile that = (ViewableProfile) o;
    return name.equals(that.name) && username.equals(that.username) && division.equals(
          that.division) && contract.equals(that.contract);
  }

  public int hashCode() {
    return Objects.hash(name, username, division, contract);
  }
}
