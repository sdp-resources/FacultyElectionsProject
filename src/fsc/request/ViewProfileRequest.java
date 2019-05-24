package fsc.request;

public class ViewProfileRequest extends Request {
  public final String username;
  public ViewProfileRequest(String username) {
    this.username = username;
  }
}
