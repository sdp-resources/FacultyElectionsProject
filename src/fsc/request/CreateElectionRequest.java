package fsc.request;

public class CreateElectionRequest extends Request {
  public final String seatName;
  public final String committeeName;

  public CreateElectionRequest(String seat, String committee) {
    this.seatName = seat;
    this.committeeName = committee;
  }
}
