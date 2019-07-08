package fsc.entity;

public class Voter {
  private final Profile voter;
  private final Election election;

  Voter(Profile voter, Election election) {
    this.voter = voter;
    this.election = election;
  }

  public boolean equals(Voter other) {
    return voter.equals(other.voter) && election.equals(other.election);
  }

  public Profile getVoter() {
    return voter;
  }

  public Election getElection() {
    return election;
  }

  public String toString() {
    return "Voter{" + voter.getUsername() + ", " + election.getID() + '}';
  }
}
