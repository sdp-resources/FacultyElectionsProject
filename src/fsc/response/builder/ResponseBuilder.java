package fsc.response.builder;

import fsc.response.Response;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ResponseBuilder<S> {
  static <S> ResponseBuilder<S> ofValue(S value) {
    return new PendingResponseBuilder<>(value);
  }
  static <S> ResponseBuilder<S> ofResponse(Response response) {
    return new ResolvedResponseBuilder<>(response);
  }

  static <S, T> Function<S, ResponseBuilder<T>> lift(Function<S, T> func) {
    return func.andThen(ResponseBuilder::ofValue);
  }

  static <S, T, R> BiFunction<S, T, ResponseBuilder<R>> lift(BiFunction<S, T, R> func) {
    return func.andThen(ResponseBuilder::ofValue);
  }

  ResponseBuilder<S> escapeIf(Function<S, Boolean> shouldEscape, Response escape);
  ResponseBuilder<S> perform(Consumer<S> consumer);
  <T> ResponseBuilder<T> mapThrough(Function<S, ResponseBuilder<T>> mapper);
  <T, R> ResponseBuilder<R> bindWith(ResponseBuilder<T> other, BiFunction<S, T, ResponseBuilder<R>> combiner);
  Response resolveWith(Function<S, Response> process);
}
