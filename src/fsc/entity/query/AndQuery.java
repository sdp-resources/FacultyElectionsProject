package fsc.entity.query;

import fsc.entity.Profile;
import fsc.entity.query.Query;

public class AndQuery implements Query {
  private Query[] queries;

  public AndQuery(Query[] queries) {
    this.queries = queries;
  }

  public boolean isProfileValid(Profile profile) {
    for(Query query : queries)
    {
      if (!query.isProfileValid(profile)) return false;
    }

    return true;
  }
}
