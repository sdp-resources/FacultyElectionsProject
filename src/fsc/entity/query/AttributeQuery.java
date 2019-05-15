package fsc.entity.query;

import fsc.entity.Profile;

public class AttributeQuery implements Query {
  private String key;
  private String value;

  public AttributeQuery(String key, String value)
  {
    this.key = key;
    this.value = value;
  }

  public boolean isProfileValid(Profile profile) {
    if (key.equals("name")) return profile.getName().equals(value);
    if (key.equals("department")) return profile.getDivision().equals(value);
    if (key.equals("contract")) return profile.getContract().equals(value);
    return false;
  }

  public String getFormattedString() { return key + " = " + value; }
}
