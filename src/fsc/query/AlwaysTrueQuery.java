package fsc.query;

import fsc.entity.Profile;

public class AlwaysTrueQuery implements Query {

  public boolean isProfileValid(Profile profile) {
    return true;
  }
}
