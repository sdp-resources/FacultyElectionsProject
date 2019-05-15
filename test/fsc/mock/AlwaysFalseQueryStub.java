package fsc.mock;

import fsc.entity.Profile;
import fsc.entity.query.Query;

public class AlwaysFalseQueryStub implements Query {

  public boolean isProfileValid(Profile profile) {
    return false;
  }

  public String getFormattedString() { return "false"; }
}
