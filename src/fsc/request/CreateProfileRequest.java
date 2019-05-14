package fsc.request;

public class CreateProfileRequest {
  public final String name;
  public final String username;
  public final String department;
  public final String contract;

  public CreateProfileRequest(
        String name, String username, String department, String contract
  ) {
    this.name = name;
    this.username = username;
    this.department = department;
    this.contract = contract;
  }
}
