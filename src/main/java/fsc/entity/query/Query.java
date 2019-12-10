package fsc.entity.query;

import fsc.entity.Profile;
import fsc.service.query.QuerySimplifier;

import java.io.Serializable;
import java.util.List;

public abstract class Query implements Serializable {
  public static Query always() { return Query.all(); }

  public static Query never() { return Query.any(); }

  public static Query any(Query... queries) { return any(List.of(queries)); }

  public static Query any(List<Query> queries) { return new OrQuery(queries); }

  public static Query all(Query... queries) { return all(List.of(queries)); }

  public static Query all(List<Query> queries) { return new AndQuery(queries); }

  public static Query has(String key, String value) { return new AttributeQuery(key, value); }

  public static Query isActive() { return has("status", "active"); }

  public static Query isInactive() { return has("status", "inactive"); }

  public static Query not(Query query) {
    return new NotQuery(query);
  }


  public static NamedQuery named(String name) {
    return named(name, null);
  }

  public static NamedQuery named(String aName, Query query) {
    return new NamedQuery(aName, query);
  }

  public abstract Object accept(QueryVisitor visitor);
  public abstract boolean isProfileValid(Profile profile);

  public Query simplify() {
    return new QuerySimplifier().visit(this);
  }

  public interface QueryVisitor<T> {
    default T visit(Query query) { return (T) query.accept(this); }
    T visit(OrQuery query);
    T visit(AndQuery query);
    T visit(AttributeQuery query);
    T visit(NotQuery query);
    T visit(NamedQuery query);
    T visit(UnknownNamedQuery query);
  }

}
