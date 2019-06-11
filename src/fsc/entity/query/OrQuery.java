package fsc.entity.query;

import fsc.entity.Profile;

import java.util.List;
import java.util.Objects;

public class OrQuery extends Query {
  public List<Query> queries;

  public OrQuery(List<Query> queries) {
    this.queries = queries;
  }

  public Object accept(Query.QueryVisitor visitor) {
    return visitor.visit(this);
  }

  public boolean isProfileValid(Profile profile) {
    for (Query query : queries) {
      if (query.isProfileValid(profile)) return true;
    }

    return false;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrQuery orQuery = (OrQuery) o;
    return queries.equals(orQuery.queries);
  }

  public int hashCode() {
    return Objects.hash(queries);
  }

  public String toString() {
    return "OrQuery{" + queries + '}';
  }
}
