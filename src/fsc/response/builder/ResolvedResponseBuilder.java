package fsc.response.builder;

import fsc.response.Response;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class ResolvedResponseBuilder<S> implements ResponseBuilder<S> {
  private Response resolvedResponse;

  public ResolvedResponseBuilder(Response response) {this.resolvedResponse = response;}

  public ResponseBuilder<S> perform(Consumer<S> consumer) {
    return this;
  }

  public ResponseBuilder<S> escapeIf(Function<S, Boolean> shouldEscape, Response escape) {
    return this;
  }

  public <T> ResponseBuilder<T> mapThrough(Function<S, ResponseBuilder<T>> mapper) {
    return (ResponseBuilder<T>) this;
  }

  public <T, R> ResponseBuilder<R> bindWith(
        ResponseBuilder<T> other, BiFunction<S, T, ResponseBuilder<R>> combiner
  ) {
    return (ResponseBuilder<R>) this;
  }

  public Response resolveWith(Function<S, Response> process) {
    return resolvedResponse;
  }

  public String toString() {
    return "ResolvedResponseBuilder{" + resolvedResponse + '}';
  }
}
