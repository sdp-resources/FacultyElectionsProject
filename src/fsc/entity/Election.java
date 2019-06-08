package fsc.entity;

import fsc.entity.query.Query;

import java.util.List;

public class Election {

  private Ballot ballot;
  private Committee committee;
  private Seat seat;
  private String ID;

  private Query defaultQuery;

  public Election(Seat seat, Committee committee, Query query, Ballot ballot) {
    this.seat = seat;
    this.committee = committee;
    this.defaultQuery = query;
    this.ballot = ballot;
  }

  public String getID() {
    return ID;
  }

  public void setID(String ID) {this.ID = ID;}

  public Seat getSeat() {
    return seat;
  }

  public Committee getCommittee() {
    return committee;
  }

  public Ballot getBallot() {
    return ballot;
  }

  public void setBallot(Ballot ballot) {
    this.ballot = ballot;
  }

  public void setDefaultQuery(Query defaultQuery) {
    this.defaultQuery = defaultQuery;
  }

  public Query getDefaultQuery() {
    return defaultQuery;
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
}

