package fsc.entity;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ballot extends AbstractList<Candidate> {

  private final List<Candidate> candidates = new ArrayList<>();

  public void addCandidate(Profile profile) {
    add(new Candidate(profile));
  }

  public void addCandidates(List<Profile> profiles) {
    for (Profile profile : profiles) {
      add(new Candidate(profile));
    }
  }

  public boolean hasCandidate(Profile profile) {
    return stream().anyMatch((c) -> c.matchesProfile(profile));
  }

  public void remove(Profile profile) throws NoProfileInBallotException {
    if (!removeIf(i -> i.matchesProfile(profile))) {
      throw new NoProfileInBallotException();
    }
  }

  public List<Profile> getCandidateProfiles() {
    return stream().filter(Candidate::hasNotDeclined)
                   .map(Candidate::getProfile).collect(Collectors.toList());
  }

  Candidate getCandidateByUsername(String username) throws NoProfileInBallotException {
    for (Candidate candidate : candidates) {
      if (candidate.matchesUsername(username)) return candidate;
    }
    throw new NoProfileInBallotException();
  }

  public int size() {
    return candidates.size();
  }

  public Candidate get(int i) {
    return candidates.get(i);
  }

  public Candidate remove(int index) {
    return candidates.remove(index);
  }

  public void add(int index, Candidate element) {
    candidates.add(index, element);
  }

  public static class NoProfileInBallotException extends Exception {}
}
