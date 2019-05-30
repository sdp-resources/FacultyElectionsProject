package fsc.entity.query;

import fsc.entity.Profile;

public class FalseQuery extends Query {

  public boolean isProfileValid(Profile profile) {
    return false;
  }

  public String getFormattedString() { return "false"; }
}
