package fsc.entity.query;

import fsc.entity.Profile;

import java.util.Objects;

public class AttributeQuery extends Query {
  private final String key;
  private final String value;

  public AttributeQuery(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public boolean isProfileValid(Profile profile) {
    if (key.equals("name")) return profile.getName().equals(value);
    if (key.equals("division")) return profile.getDivision().equals(value);
    if (key.equals("contract")) return profile.getContract().equals(value);
    if (key.equals("status")) return profile.getStatus().toString().equals(value);
    return false;
  }

  public String getFormattedString() { return key + " = " + value; }

  public String toString() {
    return "AttributeQuery{" + "key='" + key + '\'' + ", value='" + value + '\'' + '}';
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AttributeQuery that = (AttributeQuery) o;
    return Objects.equals(key, that.key) && Objects.equals(value, that.value);
  }

  public int hashCode() {
    return Objects.hash(key, value);
  }
}
