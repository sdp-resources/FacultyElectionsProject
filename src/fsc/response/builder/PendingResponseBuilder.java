package fsc.response.builder;

import fsc.response.Response;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

class PendingResponseBuilder<S> implements ResponseBuilder<S> {
  private S value;

  public PendingResponseBuilder(S value) { this.value = value; }

  public ResponseBuilder<S> escapeIf(Function<S, Boolean> shouldEscape, Response escape) {
    return shouldEscape.apply(value) ? new ResolvedResponseBuilder<>(escape) : this;
  }

  public ResponseBuilder<S> perform(Consumer<S> consumer) {
    consumer.accept(value);
    return this;
  }

  public <T> ResponseBuilder<T> mapThrough(Function<S, ResponseBuilder<T>> mapper) {
    return mapper.apply(value);
  }

  public <T, R> ResponseBuilder<R> bindWith(
        ResponseBuilder<T> other, BiFunction<S, T, ResponseBuilder<R>> combiner
  ) {
    return other.mapThrough(value2 -> combiner.apply(value, value2));
  }

  public Response resolveWith(Function<S, Response> process) {
    return process.apply(value);
  }

}
