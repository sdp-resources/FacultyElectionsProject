package fsc.entity.query;

import fsc.entity.Profile;

public class TrueQuery implements Query {

  public boolean isProfileValid(Profile profile) {
    return true;
  }

  public String getFormattedString() { return "true"; }
}