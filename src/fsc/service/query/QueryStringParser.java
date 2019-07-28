package fsc.service.query;

import fsc.entity.query.Query;

public interface QueryStringParser {
  Query parse() throws QueryParseException;

  class QueryParseException extends Exception {
    public final String message;
    public final int location;

    public QueryParseException(String message, int location) {
      super("Parse Error at " + location + ": " + message);
      this.message = message;
      this.location = location;
    }
  }
}
