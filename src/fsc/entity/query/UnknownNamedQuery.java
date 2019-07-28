package fsc.entity.query;

import fsc.entity.Profile;

public class UnknownNamedQuery extends Query {
  public Object accept(QueryVisitor visitor) {
    return visitor.visit(this);
  }

  public boolean isProfileValid(Profile profile) {
    return false;
  }
}
