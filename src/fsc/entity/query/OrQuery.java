package fsc.entity.query;

import fsc.entity.Profile;

public class OrQuery implements Query {
  private Query[] queries;

  public OrQuery(Query[] queries) {
    this.queries = queries;
  }

  public boolean isProfileValid(Profile profile) {
    for(Query query : queries)
    {
      if (query.isProfileValid(profile)) return true;
    }

    return false;
  }

  public String getFormattedString()
  {
    String output = "(";
    for(int i = 0; i < queries.length; i++)
    {
      output += queries[i].getFormattedString();
      if (i < queries.length - 1) output += " OR ";
    }
    output += ")";
    return output;
  }
}