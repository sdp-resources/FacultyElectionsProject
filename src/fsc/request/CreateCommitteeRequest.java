package fsc.request;

public class CreateCommitteeRequest extends Request {
  public final String name;
  public final String description;
  public final String voterQuery;

  public CreateCommitteeRequest(String name, String description, String voterQuery) {
    this.name = name;
    this.description = description;
    this.voterQuery = voterQuery;
  }
}
