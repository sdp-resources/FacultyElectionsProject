package fsc.entity;

import fsc.entity.query.Query;
import fsc.gateway.ElectionGateway;

import java.time.LocalDateTime;
import java.util.*;

public class Election {

  private Collection<Candidate> candidates = new ArrayList<>();
  private Seat seat;
  private LocalDateTime date;
  private Long ID;
  private State state;
  private Query candidateQuery;
  private Collection<Voter> voters = new ArrayList<>();
  private Collection<VoteRecord> voteRecords = new ArrayList<>();

  // TODO: Election states
  // 0. DONE Election state can be changed with ChangeElectionStateRequest
  // 1. Setup:
  //    - DONE Admin can do BallotChangeRequest
  //    - DONE Admin can do AddCandidateRequest
  //    - DONE Admin can do RemoveCandidateRequest
  //    - DONE Candidates do not see DTS status
  //    - DONE Voters cannot vote
  // 2. DecideToStand:
  //    - DONE Candidate can view and change their DTS status
  //    - DONE Admin cannot change candidates or add/remove candidates
  //    - DONE Voters cannot vote
  // 3. Vote:
  //    - DONE Candidates that did not decide are automatically accepted (email notification?)
  //    - DONE Voters can see the election and vote
  //    - DONE Candidates cannot change their DTS status
  //    - DONE Admin cannot change candidates or add/remove candidates
  // 4. Closed:
  //    - Stores the election tally record
  //    - DONE Voters cannot vote
  //    - DONE Candidates cannot change their DTS status
  //    - DONE Admin cannot change candidates or add/remove candidates
  //    - Admin can view election results

  public Election() { }

  public Election(Seat seat) {
    this.seat = seat;
    this.candidateQuery = seat.getCandidateQuery();
    this.state = State.Setup;
    this.date = LocalDateTime.now();
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

  public Collection<Candidate> getCandidates() {
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

  public boolean hasCandidate(String username) {
    return candidates.stream().anyMatch((c) -> c.matchesUsername(username));
  }

  public void removeCandidate(Profile profile) {
    candidates.removeIf(i -> i.matchesProfile(profile));
  }

  public Collection<Profile> getCandidateProfiles() {
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

  public Collection<Voter> getVoters() {
    return voters;
  }

  public void setVoters(Collection<Voter> voters) {
    this.voters = voters;
  }

  public Collection<VoteRecord> getVoteRecords() {
    return voteRecords;
  }

  public void setVoteRecords(Collection<VoteRecord> voteRecords) {
    this.voteRecords = voteRecords;
  }

  public boolean isInSetupState() {
    return state.equals(State.Setup);
  }

  public boolean isActive() {
    return state.equals(State.DecideToStand) || state.equals(State.Vote);
  }

  public boolean isInVoteState() {
    return state.equals(State.Vote);
  }

  public boolean isInDecideToStandState() {
    return state.equals(State.DecideToStand);
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

  public void addVoteRecord(VoteRecord voteRecord) {
    this.voteRecords.add(voteRecord);
  }

  public enum State {
    Setup, DecideToStand, Vote, Closed

  }
}

