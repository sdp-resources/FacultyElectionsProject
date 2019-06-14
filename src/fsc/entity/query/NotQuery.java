package fsc.entity.query;

import fsc.entity.Profile;

import java.util.Objects;

public class NotQuery extends Query {
  public Query query;

  public NotQuery(Query query) {
    this.query = query;
  }

  public Object accept(QueryVisitor visitor) {
    return visitor.visit(this);
  }

  public boolean isProfileValid(Profile profile) {
    return !query.isProfileValid(profile);
  }

  public String toString() {
    return "NotQuery{" + query + '}';
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NotQuery notQuery = (NotQuery) o;
    return query.equals(notQuery.query);
  }

  public int hashCode() {
    return Objects.hash(query);
  }
}
