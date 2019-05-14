package fsc.query;

import fsc.entity.Profile;

public class AlwaysFalseQuery implements Query {

  public boolean isProfileValid(Profile profile){
    return false;
  }

}
