package fsc.interactor.fetcher;

import fsc.entity.query.Query;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.query.*;
import fsc.utils.builder.Builder;

public class QueryFetcher {
  private QueryStringConverter converter;

  public QueryFetcher(NameValidator validator) {
    converter = new QueryStringConverter(new ValidatingQueryStringParserFactory(validator));
  }

  public Builder<Query, Response> createFromString(String queryString) {
    try {
      return Builder.ofValue(converter.fromString(queryString));
    } catch (QueryStringParser.QueryParseException e) {
      return Builder.ofResponse(ResponseFactory.invalidQuery(e.getMessage()));
    }
  }
}
