package fsc.service.query;

import fsc.entity.query.*;

public class QueryStringConverter implements Query.QueryVisitor {

  public String visit(AndQuery query) {
    if (query.queries.length == 0) { return "all"; }
    String output = "(";
    for (int i = 0; i < query.queries.length; i++) {
      output += visit(query.queries[i]);
      if (i < query.queries.length - 1) output += " AND ";
    }
    output += ")";
    return output;
  }

  public String visit(OrQuery query) {
    if (query.queries.length == 0) { return "none"; }
    String output = "(";
    for (int i = 0; i < query.queries.length; i++) {
      output += visit(query.queries[i]);
      if (i < query.queries.length - 1) output += " OR ";
    }
    output += ")";
    return output;
  }

  public String visit(AttributeQuery query) {
    return query.key + " equals " + query.value;
  }

  public Query fromString(String s) {
    return new QueryStringParser(s).parse();
  }

  public String toString(Query query) {
    return (String) visit(query);
  }

}
