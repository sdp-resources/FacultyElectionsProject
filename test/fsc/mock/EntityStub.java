package fsc.mock;

import fsc.entity.Ballot;
import fsc.entity.Election;
import fsc.entity.Profile;

import java.util.List;

public class EntityStub {

  private static List<Profile> profiles = List.of(
        new Profile("John Collins", "collins", "Natural Sciences", "tenured"),
        new Profile("Barb Wahl", "wahl", "Natural Sciences", "tenured"),
        new Profile("Theresa Wilson", "wilsont", "Natural Sciences", "tenure-track"),
        new Profile("Haris Skiadas", "skiadas", "Natural Sciences", "tenured")
  );

  public static Election simpleBallotElection() {
    return new Election(null, null, null, new Ballot());
  }

  public static Election simpleBallotElection(Ballot ballot) {
    return new Election(null, null, null, ballot);
  }

  public static Profile getProfile(int i) {
    return new Profile("name" + i, "username" + i, "division", "contract");
  }

}
