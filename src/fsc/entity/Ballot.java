package fsc.entity;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class Ballot extends AbstractList<Profile> {

  private final AbstractList<Profile> profiles = new ArrayList<>();
  private final List<Candidate> candidates = new ArrayList<>();

  private String ballotID;

  public String getBallotID(String ballotID) {
    return ballotID;
  }

  public void setBallotID(String ballotID) {
    this.ballotID = ballotID;
  }

  public int size() {
    return profiles.size();
  }

  public int sizeCandidates() {return candidates.size();}

  public boolean isEmpty() {
    return candidates.isEmpty();
  }

  public boolean add(Profile profile) {
    candidates.add(new Candidate(profile));
    return profiles.add(profile);
  }

  public Candidate getCandidate(int i) {
    return candidates.get(i);
  }

  public Profile get(int i) {return profiles.get(i);}

  public void remove(Profile profile) throws NoProfileInBallotException {
    if (!profiles.remove(profile)) {
      throw new NoProfileInBallotException();
    }
    candidates.removeIf(i -> (i.getProfile() == profile));
  }

  public class NoProfileInBallotException extends Exception {}
}
