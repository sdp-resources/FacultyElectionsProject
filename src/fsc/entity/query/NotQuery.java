package fsc.entity.query;

import fsc.entity.Profile;

public class NotQuery extends Query {
  private Query query;

  public NotQuery(Query query) {
    this.query = query;
  }

  public Object accept(QueryVisitor visitor) {
    return visitor.visit(this);
  }

  public boolean isProfileValid(Profile profile) {
    return !query.isProfileValid(profile);
  }
}
