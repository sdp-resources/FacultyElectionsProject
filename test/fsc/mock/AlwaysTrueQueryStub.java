package fsc.mock;

import fsc.entity.Profile;
import fsc.entity.query.Query;

public class AlwaysTrueQueryStub implements Query {

  public boolean isProfileValid(Profile profile) {
    return true;
  }

  public String getFormattedString() { return "true"; }
}
