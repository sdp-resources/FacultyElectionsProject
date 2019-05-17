package fsc.viewable;

public class ViewableProfile {
  public final String Name;
  public final String Username;
  public final String Division;
  public final String Contract;

  public ViewableProfile(String name, String username, String division, String contract) {
    Name = name;
    Username = username;
    Division = division;
    Contract = contract;
  }

  @Override
  public boolean equals(Object other)
  {
    if (other == null) return false;
    return this.hashCode() == other.hashCode();
  }

  @Override
  public int hashCode()
  {
    return Name.hashCode() + Username.hashCode() + Division.hashCode() + Contract.hashCode();
  }
}
