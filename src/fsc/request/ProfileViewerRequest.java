package fsc.request;

public class ProfileViewerRequest extends Request {
  public String username;
  public ProfileViewerRequest(String username) {
    this.username = username;
  }
}
