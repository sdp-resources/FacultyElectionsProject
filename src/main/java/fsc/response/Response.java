package fsc.response;

import java.util.function.Function;

public interface Response<T> {
  String VALUE_ERROR_MESSAGE = "Only successful responses may have values";
  boolean isSuccessful();
  default T getValues() { throw new RuntimeException(VALUE_ERROR_MESSAGE); }
  T getValues(Function<String, T> failureHandler);
}
