package fsc.entity.query;

import fsc.entity.Profile;

import java.util.*;

public class AndQuery extends Query {
  public List<Query> queries;

  public AndQuery(List<Query> queries) {
    this.queries = new ArrayList<>(queries);
  }

  public Object accept(QueryVisitor visitor) {
    return visitor.visit(this);
  }

  public boolean isProfileValid(Profile profile) {
    for (Query query : queries) {
      if (!query.isProfileValid(profile)) return false;
    }

    return true;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AndQuery andQuery = (AndQuery) o;
    return queries.equals(andQuery.queries);
  }

  public int hashCode() {
    return Objects.hash(queries);
  }

  public String toString() {
    return "AndQuery{" + queries + '}';
  }
}
