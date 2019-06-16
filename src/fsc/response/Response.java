package fsc.response;

public interface Response {
  String VALUE_ERROR_MESSAGE = "Only successful responses may have values";
  boolean isSuccessful();
  default <T> T getValues() { throw new RuntimeException(VALUE_ERROR_MESSAGE); }
}
