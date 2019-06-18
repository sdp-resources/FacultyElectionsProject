package fsc.response.builder;

import fsc.response.Response;

import java.util.function.Function;

public interface UniPartialResponse<S> {
  Response resolveWith(Function<S, Response> process);

  <T> UniPartialResponse<T> map(Function<S, T> mapper);
}
