package fsc.entity;

public class Election {

  private Ballot ballot = new Ballot();
  // should have status:preparing, declined, voting, closed-can be enums //
  private String committee;
  private String seat;
  private int ID;

  public Election(String seat, String committee)
  {
    this.seat = seat;
    this. committee = committee;
  }

  public int getID(){
    return ID;
  }

  public String getSeat(){
    return seat;
  }

  public String getCommittee(){
    return committee;
  }

  public Ballot getBallot(){
    return ballot;
  }
}
