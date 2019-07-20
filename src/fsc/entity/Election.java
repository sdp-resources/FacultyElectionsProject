package fsc.entity;

import fsc.entity.query.Query;
import fsc.gateway.ElectionGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Election {

  private List<Candidate> candidates = new ArrayList<>();
  private Seat seat;
  private Long ID;
  private State state;
  private Query candidateQuery;
  private List<Voter> voters;

  // TODO: Add Voter relationship
  // TODO: Add Votes relationship
  // TODO: Add dates to elections?

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
  //    - Admin cannot change candidates or add/remove candidates
  //    - Voters cannot vote
  // 3. Vote:
  //    - Candidates that did not decide are automatically accepted (email notification?)
  //    - Voters can see the election and vote
  //    - Candidates cannot change their DTS status
  //    - Admin cannot change candidates or add/remove candidates
  // 4. Closed:
  //    - Voters cannot vote
  //    - Candidates cannot change their DTS status
  //    - Admin cannot change candidates or add/remove candidates
  //    - Admin can view election results

  public Election(Seat seat) {
    this.seat = seat;
    this.candidateQuery = seat.getCandidateQuery();
    this.state = State.Setup;
    setVoters(new ArrayList<>());
  }

  public Election() {
    setVoters(new ArrayList<>());
  }

  public Long getID() {
    return ID;
  }

  public void setID(Long ID) {this.ID = ID;}

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public Seat getSeat() {
    return seat;
  }

  public List<Candidate> getCandidates() {
    return candidates;
  }

  public void setCandidates(List<Candidate> candidates) {
    this.candidates = candidates;
  }

  public void setCandidateQuery(Query candidateQuery) {
    this.candidateQuery = candidateQuery;
  }

  public Query getCandidateQuery() {
    return candidateQuery;
  }

  public Candidate getCandidateByUsername(String username)
        throws ElectionGateway.NoProfileInBallotException {
    for (Candidate candidate : candidates) {
      if (candidate.matchesUsername(username)) return candidate;
    }
    throw new ElectionGateway.NoProfileInBallotException();
  }

  public void addCandidate(Candidate candidate) {
    candidates.add(candidate);
    candidate.setElection(this);
  }

  public boolean hasCandidate(Profile profile) {
    return candidates.stream().anyMatch((c) -> c.matchesProfile(profile));
  }

  public void removeCandidate(Profile profile) {
    candidates.removeIf(i -> i.matchesProfile(profile));
  }

  public List<Profile> getCandidateProfiles() {
    return Candidate.toProfiles(candidates);
  }

  public void addVoter(Voter voter) {
    getVoters().add(voter);
    voter.setElection(this);
  }

  public void removeVoter(Voter voter) {
    getVoters().remove(voter);
  }

  public Voter getVoter(Profile profile) {
    for (Voter voter : getVoters()) {
      if (voter.getProfile().equals(profile)) { return voter; }
    }

    return null;
  }

  public List<Voter> getVoters() {
    return voters;
  }

  public void setVoters(List<Voter> voters) {
    this.voters = voters;
  }

  public boolean isInSetupState() {
    return state.equals(State.Setup);
  }


  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Election election = (Election) o;
    return seat.equals(election.seat) &&
                 ID.equals(election.ID) &&
                 state == election.state &&
                 candidateQuery.equals(election.candidateQuery);
  }

  public int hashCode() {
    return Objects.hash(seat, ID, state, candidateQuery);
  }

  public enum State {
    Setup, DecideToStand, Vote, Closed;

  }
}

