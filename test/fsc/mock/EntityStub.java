package fsc.mock;

import fsc.app.AppContext;
import fsc.entity.*;

public class EntityStub {

  public static Election simpleBallotElection() {
    return AppContext.getEntityFactory().createElection(null, null, null, new Ballot());
  }

  public static Election simpleBallotElection(Ballot ballot) {
    return AppContext.getEntityFactory().createElection(null, null, null, ballot);
  }

  public static Profile getProfile(int i) {
    return AppContext.getEntityFactory()
                     .createProfile("name" + i, "username" + i, "division", "contract");
  }

  public static Profile getProfile(String username) {
    return AppContext.getEntityFactory().createProfile(username, username, "division", "contract");
  }
}
