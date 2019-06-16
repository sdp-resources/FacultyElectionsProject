package fsc.response;

import java.util.Objects;

public class ViewResponse<T> implements Response {

  private final T values;

  ViewResponse(T values) {
    this.values = values;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewResponse<?> that = (ViewResponse<?>) o;
    return Objects.equals(getValues(), that.getValues());
  }

  public int hashCode() {
    return Objects.hash(getValues());
  }

  public String toString() {
    return "ViewResponse{" + "values=" + getValues() + '}';
  }

  public boolean isSuccessful() {
    return true;
  }

  public T getValues() {
    return values;
  }
}
