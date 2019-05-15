package fsc.response;

public class ErrorResponse implements Response {
  public String response;
  public ErrorResponse(String s) {
    response = s;
  }
}
