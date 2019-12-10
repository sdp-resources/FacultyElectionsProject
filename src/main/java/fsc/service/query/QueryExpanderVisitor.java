package fsc.service.query;

import fsc.entity.query.*;

import java.util.List;
import java.util.stream.Collectors;

public class QueryExpanderVisitor implements Query.QueryVisitor<Query> {
  public Query visit(OrQuery query) {
    return Query.any(recurseOn(query.queries));
  }

  public Query visit(AndQuery query) {
    return Query.all(recurseOn(query.queries));
  }

  public Query visit(AttributeQuery query) {
    return query;
  }

  public Query visit(NotQuery query) {
    return query;
  }

  public Query visit(NamedQuery query) {
    return visit(query.query);
  }

  public Query visit(UnknownNamedQuery query) {
    throw new RuntimeException("The contents of an unspecified named query were examined. " +
                                     "This should not be happening");
  }

  private List<Query> recurseOn(List<Query> queries) {
    return queries.stream().map(this::visit).collect(Collectors.toList());
  }
}
