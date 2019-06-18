package fsc.response.builder;

import fsc.response.Response;

public class PartialResponse {

  public static <S> UniPartialResponse<S> supply(S value) {
    return new PendingUniPartialResponse<S>(value);
  }

  public static <S> UniPartialResponse<S> resolve(Response response) {
    return new ResolvedUniPartialResponse<S>(response);
  }
}