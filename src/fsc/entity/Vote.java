package fsc.entity;

import java.util.ArrayList;
import java.util.List;

public class Vote {

  private final Ballot ballot;
  List<Profile> listOfVotes = new ArrayList<>();

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
}
