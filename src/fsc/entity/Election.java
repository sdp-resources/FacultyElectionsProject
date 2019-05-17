package fsc.entity;

public class Election {

  private Ballot ballot = new Ballot();
  private Committee committee;
  private Seat seat;

  public boolean doesExist(){
    return true;
  }
}
