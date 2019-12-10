package fsc.entity.query;

import fsc.entity.Profile;

import java.util.Objects;

public class NamedQuery extends Query {
  public String name;
  public Query query;

  public NamedQuery() {}

  public NamedQuery(String name, Query query) {
    this.name = name;
    this.query = query;
  }

  public Object accept(QueryVisitor visitor) {
    return visitor.visit(this);
  }

  public boolean isProfileValid(Profile profile) {
    if (query == null) {
      throw new RuntimeException("Should not work with named query without " +
                                       "populating its enclosing query");
    }
    return query.isProfileValid(profile);
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NamedQuery that = (NamedQuery) o;
    return name.equals(that.name);
  }

  public int hashCode() {
    return Objects.hash(name);
  }

  public String toString() {
    return "NamedQuery{" + name + '}';
  }

}
