package fsc.entity;

public class Election {

  private Ballot ballot = new Ballot();
  // should have status:preparing, declined, voting, closed-can be enums //
  private Committee committee;
  private Seat seat;
  private int ID;

  public Election(Seat seat, Committee committee)
  {
    this.seat = seat;
    this. committee = committee;
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
}
