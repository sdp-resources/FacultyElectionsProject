package fsc.entity;

import fsc.entity.query.Query;

import java.util.List;

public class Election {

  private Ballot ballot;
  private Seat seat;
  private String ID;
  private State state;
  private Query candidateQuery;

  // TODO: Add Voter relationship
  // TODO: Add Votes relationship

  // TODO: Election states
  // 0. Election state can be changed with ChangeElectionStateRequest
  // 1. Setup:
  //    - Admin can do BallotChangeRequest
  //    - Admin can do AddCandidateRequest
  //    - Admin can do RemoveCandidateRequest
  //    - Candidates do not see DTS status
  //    - Voters cannot vote
  // 2. DecideToStand:
  //    - Candidate can view and change their DTS status
  //    - Admin cannot change ballot or add/remove candidates
  //    - Voters cannot vote
  // 3. Vote:
  //    - Candidates that did not decide are automatically accepted (email notification?)
  //    - Voters can see the election and vote
  //    - Candidates cannot change their DTS status
  //    - Admin cannot change ballot or add/remove candidates
  // 4. Closed:
  //    - Voters cannot vote
  //    - Candidates cannot change their DTS status
  //    - Admin cannot change ballot or add/remove candidates
  //    - Admin can view election results

  public Election(Seat seat, Query candidateQuery, Ballot ballot) {
    this.seat = seat;
    this.candidateQuery = candidateQuery;
    this.ballot = ballot;
    this.state = State.Setup;
  }

  public Election() {

  }

  public String getID() {
    return ID;
  }

  public void setID(String ID) {this.ID = ID;}

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public Seat getSeat() {
    return seat;
  }

  public Ballot getBallot() {
    return ballot;
  }

  public void setBallot(Ballot ballot) {
    this.ballot = ballot;
  }

  public void setCandidateQuery(Query candidateQuery) {
    this.candidateQuery = candidateQuery;
  }

  public Query getCandidateQuery() {
    return candidateQuery;
  }

  public Candidate getCandidateByUsername(String username)
        throws Ballot.NoProfileInBallotException {
    return ballot.getCandidateByUsername(username);
  }

  public boolean hasCandidate(Profile profile) {
    return ballot.hasCandidate(profile);
  }

  public List<Profile> getCandidateProfiles() {
    return ballot.getCandidateProfiles();
  }

  public enum State {
    Setup, DecideToStand, Vote, Closed
  }
}

