package fsc.entity;

import java.util.ArrayList;
import java.util.List;

public class Vote {

  private final Ballot ballot;
  final List<Profile> listOfVotes = new ArrayList<>();

  public Vote(Ballot ballot){
    this.ballot = ballot;
  }

  public void addSingleVote(Profile profile) {
    if (ballot.contains(profile)) {
      listOfVotes.add(profile);
    }
  }

  public List<Profile> getRankedList() {
    return listOfVotes;
  }

  public void addMultipleVote(List<Profile> listOfCandidates) {
    for (Profile profile: listOfCandidates){
      this.addSingleVote(profile);
    }
  }

  public void removeProfileFromVote(Profile profile) {
    listOfVotes.remove(profile);
  }

  public void removeMultipleVotes(List<Profile> listOfRemovals) {
    for (Profile profile: listOfRemovals){
      this.removeProfileFromVote(profile);
    }
  }
}
