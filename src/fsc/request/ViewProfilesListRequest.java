package fsc.request;

public class ViewProfilesListRequest extends Request {
  public String which;

  public ViewProfilesListRequest(String which) {
    this.which = which;
  }

  public ViewProfilesListRequest() {
    this("all");
  }
}
