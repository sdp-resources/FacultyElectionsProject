package fsc.request;

public class CreateCommitteeRequest extends Request {
  public final String name;
  public final String description;

  public CreateCommitteeRequest(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
