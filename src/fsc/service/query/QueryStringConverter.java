package fsc.service.query;

import fsc.entity.query.*;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class QueryStringConverter implements Query.QueryVisitor<String> {

  public String visit(AndQuery query) {
    if (query.queries.size() == 0) { return "all"; }
    return query.queries.stream().map(this::visit)
                        .collect(joinCollector(" and "));
  }

  public String visit(OrQuery query) {
    int length = query.queries.size();
    if (length == 0) { return "none"; }
    return query.queries.stream().map(this::visit)
                        .collect(joinCollector(" or "));
  }

  private Collector<CharSequence, ?, String> joinCollector(String delimiter) {
    return Collectors.joining(delimiter, "(", ")");
  }

  public String visit(AttributeQuery query) {
    return query.key + " equals \"" + query.value + "\"";
  }

  public String visit(NotQuery query) {
    return "(not " + visit(query.query) + ")";
  }

  public String visit(NamedQuery query) {
    return query.name;
  }

  public Query fromString(String s) throws QueryStringParser.QueryParseException {
    return new QueryStringParser(s).parse();
  }

  public String toString(Query query) { return visit(query); }

  public QueryValidationResult validateQueryString(String queryString) {
    try {
      Query query = fromString(queryString);
      return new QueryValidationResult.ValidQueryResult(query, toString(query));
    } catch (QueryStringParser.QueryParseException e) {
      return new QueryValidationResult.InvalidQueryResult(e.getMessage());
    }
  }
}
