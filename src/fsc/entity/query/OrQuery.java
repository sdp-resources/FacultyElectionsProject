package fsc.entity.query;

import fsc.entity.Profile;
import fsc.entity.query.Query;

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
}
