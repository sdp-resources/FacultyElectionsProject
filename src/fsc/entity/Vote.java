package fsc.entity;

import java.util.ArrayList;
import java.util.List;

public class Vote {

  public final Election election;
  private final List<Profile> listOfVotes = new ArrayList<>();

  public Vote(Election election) {
    this.election = election;
  }

  public List<Profile> getRankedList() {
    return listOfVotes;
  }

  public void addVotes(List<Profile> listOfCandidates) {
    listOfVotes.addAll(listOfCandidates);
  }

  public void removeProfileFromVote(Profile profile) {
    listOfVotes.remove(profile);
  }

  public void removeMultipleVotes(List<Profile> listOfRemovals) {
    for (Profile profile : listOfRemovals) {
      this.removeProfileFromVote(profile);
    }
  }
}
