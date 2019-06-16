package fsc.response;

import java.util.Objects;

public class ViewResponse<T> implements Response {

  public final T values;

  ViewResponse(T values) {
    this.values = values;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewResponse<?> that = (ViewResponse<?>) o;
    return Objects.equals(values, that.values);
  }

  public int hashCode() {
    return Objects.hash(values);
  }

  public String toString() {
    return "ViewResponse{" + "values=" + values + '}';
  }

  public boolean isSuccessful() {
    return true;
  }
}
