package fsc.mock;

import fsc.entity.*;

public class EntityStub {

  private static EntityFactory entityFactory = new SimpleEntityFactory();

  public static Election simpleBallotElection() {
    return entityFactory.createElection(null, null, entityFactory.createBallot());
  }

  public static Election simpleBallotElection(Ballot ballot) {
    return entityFactory.createElection(null, null, ballot);
  }

  public static Profile getProfile(int i) {
    return entityFactory
                     .createProfile("name" + i, "username" + i, "division", "contract");
  }

  public static Profile getProfile(String username) {
    return entityFactory.createProfile(username, username, "division", "contract");
  }
}
