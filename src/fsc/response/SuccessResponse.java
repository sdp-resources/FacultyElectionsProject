package fsc.response;

public class SuccessResponse implements Response {
  public boolean equals(Object o) {
    if (this == o) return true;
    return (o != null && getClass() == o.getClass());
  }
}
