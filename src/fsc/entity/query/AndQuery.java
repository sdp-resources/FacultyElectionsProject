package fsc.entity.query;

import fsc.entity.Profile;

import java.util.Arrays;

public class AndQuery extends Query {
  public final Query[] queries;

  public AndQuery(Query[] queries) {
    this.queries = queries;
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
    return Arrays.equals(queries, andQuery.queries);
  }

  public int hashCode() {
    return Arrays.hashCode(queries);
  }
}
