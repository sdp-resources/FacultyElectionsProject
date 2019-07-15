package fsc.interactor.fetcher;

import fsc.entity.query.Query;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;
import fsc.utils.builder.Builder;

public class QueryFetcher {
  public Builder<Query, Response> createFromString(
        String queryString
  ) {
    try {
      return Builder.ofValue(new QueryStringConverter().fromString(queryString));
    } catch (QueryStringParser.QueryParseException e) {
      return Builder.ofResponse(ResponseFactory.invalidQuery(e.getMessage()));
    }
  }
}
