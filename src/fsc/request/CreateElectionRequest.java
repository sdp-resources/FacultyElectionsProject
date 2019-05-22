package fsc.request;

public class CreateElectionRequest extends Request {
  public String seatName;
  public String committeeName;
  public CreateElectionRequest(String seat, String committee){
    this.seatName = seat;
    this.committeeName = committee;
  }
}
