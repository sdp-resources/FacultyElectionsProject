package fsc.mock;

import fsc.entity.Ballot;
import fsc.entity.Election;
import fsc.entity.Profile;

import java.util.List;

public class EntityStub {

  public static Election simpleBallotElection() {
    return new Election(null, null, null, new Ballot());
  }

  public static Election simpleBallotElection(Ballot ballot) {
    return new Election(null, null, null, ballot);
  }

  public static Profile getProfile(int i) {
    return new Profile("name" + i, "username" + i, "division", "contract");
  }

  public static Profile getProfile(String username) {
    return new Profile(username, username, "division", "contract");
  }
}
