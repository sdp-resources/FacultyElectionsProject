package fsc.response.builder;

import fsc.response.Response;

import java.util.function.Function;

public class ResolvedUniPartialResponse<S> implements UniPartialResponse<S> {
  private Response response;

  public ResolvedUniPartialResponse(Response response) {this.response = response;}

  public Response resolveWith(Function<S, Response> process) {
    return response;
  }

  public <T> UniPartialResponse<T> map(Function<S, T> mapper) {
    return (UniPartialResponse<T>) this;
  }
}
