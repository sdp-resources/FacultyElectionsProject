package fsc.entity;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class Vote extends AbstractList<Profile> {

  private final List<Profile> preferenceList = new ArrayList<>();

  public Vote(List<Profile> profiles) {
    preferenceList.addAll(profiles);
  }

  public Vote() { }

  public int size() {
    return preferenceList.size();
  }

  public Profile get(int index) {
    return preferenceList.get(index);
  }

  public static Vote of(Profile... profiles) {
    return new Vote(List.of(profiles));
  }

  public List<Profile> getRankedList() {
    return preferenceList;
  }

  public void addVotes(List<Profile> listOfCandidates) {
    preferenceList.addAll(listOfCandidates);
  }

  public void removeProfileFromVote(Profile profile) {
    preferenceList.remove(profile);
  }

  public void removeMultipleVotes(List<Profile> listOfRemovals) {
    for (Profile profile : listOfRemovals) {
      this.removeProfileFromVote(profile);
    }
  }

  public Vote clone() {
    return new Vote(this);
  }

}
