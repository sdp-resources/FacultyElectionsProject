package fsc.request;

public class CreateCommitteeRequest extends Request {
  public String name;
  public String description;
  public CreateCommitteeRequest(String name, String description){
    this.name = name;
    this.description = description;
  }
}
