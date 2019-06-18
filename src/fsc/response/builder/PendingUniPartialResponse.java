package fsc.response.builder;

import fsc.response.Response;

import java.util.function.Function;

class PendingUniPartialResponse<S> implements UniPartialResponse<S> {
  private S value;

  public PendingUniPartialResponse(S value) { this.value = value; }

  public Response resolveWith(Function<S, Response> process) {
    return process.apply(value);
  }

  public <T> UniPartialResponse<T> map(Function<S, T> mapper) {
    return new PendingUniPartialResponse<>(mapper.apply(value));
  }
}
