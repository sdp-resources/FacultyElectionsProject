package fsc.request;

public class ViewDivisionRequest extends Request {
  public final String divisionName;

  public ViewDivisionRequest(String divisionName){
    this.divisionName = divisionName;
  }
}
