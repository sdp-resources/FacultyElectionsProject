package fsc.request;

public class CreateElectionRequest {
  public String seatName;
  public String committeeName;
  public CreateElectionRequest(String seat, String committee){
    this.seatName = seat;
    this. committeeName = committee;
  }
}
