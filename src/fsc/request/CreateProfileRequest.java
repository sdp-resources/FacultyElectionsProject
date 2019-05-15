package fsc.request;

public class CreateProfileRequest {
  public final String name;
  public final String username;
  public final String division;
  public final String contract;

  public CreateProfileRequest(
        String name, String username, String division, String contract
  ) {
    this.name = name;
    this.username = username;
    this.division = division;
    this.contract = contract;
  }
}
