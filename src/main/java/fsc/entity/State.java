package fsc.entity;

public enum State {
  Setup, DecideToStand, Vote, Closed;

  public String getString() {
    return this.toString();
  }

  public boolean isSetup() {
    return equals(Setup);
  }

  public boolean isDecideToStand() {
    return equals(DecideToStand);
  }

  public boolean isVote() {
    return equals(Vote);
  }

  public boolean canChangeVoters() {
    return isSetup() || isDecideToStand() || isVote();
  }

  public Boolean canChangeCandidates() {
    return isSetup() || isDecideToStand();
  }

  public boolean test() {
    return true;
  }

  public boolean isActive() {
    return isDecideToStand() || isVote();
  }
}
