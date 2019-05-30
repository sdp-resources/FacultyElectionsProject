package fsc.entity.query;

import fsc.entity.Profile;

import java.util.Arrays;

public class OrQuery extends Query {
  private final Query[] queries;

  public OrQuery(Query[] queries) {
    this.queries = queries;
  }

  public boolean isProfileValid(Profile profile) {
    for (Query query : queries) {
      if (query.isProfileValid(profile)) return true;
    }

    return false;
  }

  public String getFormattedString() {
    if (queries.length == 0) { return "none"; }
    String output = "(";
    for (int i = 0; i < queries.length; i++) {
      output += queries[i].getFormattedString();
      if (i < queries.length - 1) output += " OR ";
    }
    output += ")";
    return output;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrQuery orQuery = (OrQuery) o;
    return Arrays.equals(queries, orQuery.queries);
  }

  public int hashCode() {
    return Arrays.hashCode(queries);
  }
}
