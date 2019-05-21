package fsc.entity;

import fsc.entity.query.Query;

public class Election {

  private Ballot ballot;
  // should have status:preparing, declined, voting, closed-can be enums //
  private Committee committee;
  private Seat seat;
  private int ID;

  private Query defaultQuery;

  public Election(Seat seat, Committee committee, Query query, Ballot ballot)
  {
    this.seat = seat;
    this.committee = committee;
    this.defaultQuery = query;
    this.ballot = ballot;
  }

  public int getID(){
    return ID;
  }

  public Seat getSeat(){
    return seat;
  }

  public Committee getCommittee(){
    return committee;
  }

  public Ballot getBallot(){
    return ballot;
  }

  public void setDefaultQuery(Query defaultQuery) {
    this.defaultQuery = defaultQuery;
  }

  public Query getDefaultQuery() {
    return defaultQuery;
  }
}

