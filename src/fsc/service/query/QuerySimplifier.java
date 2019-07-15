package fsc.service.query;

import fsc.entity.query.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuerySimplifier implements Query.QueryVisitor<Query> {
  public Query visit(OrQuery query) {
    return Query.any(flatten(query.queries, this::flattenOr));
  }

  public Query visit(AndQuery query) {
    return Query.all(flatten(query.queries, this::flattenAnd));
  }

  public Query visit(AttributeQuery query) {
    return query;
  }

  public Query visit(NotQuery query) {
    return query;
  }

  public Query visit(NamedQuery query) { return query; }

  private Stream<Query> flattenOr(Query q) {
    return (q instanceof OrQuery ? ((OrQuery) q).queries : List.of(q)).stream();
  }

  private Stream<Query> flattenAnd(Query q) {
    return (q instanceof AndQuery ? ((AndQuery) q).queries : List.of(q)).stream();
  }

  private List<Query> flatten(List<Query> list, Function<Query, Stream<? extends Query>> helper) {
    return list.stream().map(this::visit).flatMap(helper).collect(Collectors.toList());
  }
}
