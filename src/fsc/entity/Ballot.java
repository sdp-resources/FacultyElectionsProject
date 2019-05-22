package fsc.entity;

import java.util.ArrayList;
import java.util.AbstractList;

public class Ballot extends AbstractList<Profile> {

  private AbstractList<Profile> profiles = new ArrayList<>();

  public String ballotID;

  public String getBallotID(String ballotID) {
    return ballotID;
  }

  public void setBallotID(String ballotID) {
    this.ballotID = ballotID;
  }

  public int size() {
    return profiles.size();
  }

  public boolean isEmpty() {
    return profiles.isEmpty();
  }

  public boolean add(Profile profile) {
    return profiles.add(profile);
  }

  public Profile get(int i) {
    return profiles.get(i);
  }

  public void remove(Profile profile) throws NoProfileInBallotException {
    if (!profiles.remove(profile))
    {
      throw new NoProfileInBallotException();
    }

  }

  public class NoProfileInBallotException extends Exception {}
}
