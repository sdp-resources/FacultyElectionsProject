package fsc.request;

public class CreateElectionRequest {
  public String seat;
  public String committee;
  public CreateElectionRequest(String seat, String committee){
    this.seat = seat;
    this. committee = committee;
  }
}
