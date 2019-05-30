package fsc.entity.query;

import fsc.entity.Profile;

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
    String output = "(";
    for (int i = 0; i < queries.length; i++) {
      output += queries[i].getFormattedString();
      if (i < queries.length - 1) output += " AND ";
    }
    output += ")";
    return output;
  }
}
