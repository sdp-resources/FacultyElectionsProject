package fsc.entity.query;

import fsc.entity.Profile;

import java.util.Arrays;

public class AndQuery extends Query {
  private final Query[] queries;

  public AndQuery(Query[] queries) {
    this.queries = queries;
  }

  public boolean isProfileValid(Profile profile) {
    for (Query query : queries) {
      if (!query.isProfileValid(profile)) return false;
    }

    return true;
  }

  public String getFormattedString() {
    if (queries.length == 0) { return "all"; }
    String output = "(";
    for (int i = 0; i < queries.length; i++) {
      output += queries[i].getFormattedString();
      if (i < queries.length - 1) output += " AND ";
    }
    output += ")";
    return output;
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
