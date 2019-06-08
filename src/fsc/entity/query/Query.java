package fsc.entity.query;

import fsc.entity.Profile;

import java.util.List;

public abstract class Query {
  public static Query always() { return Query.all(); }

  public static Query never() { return Query.any(); }

  public static Query any(Query... queries) { return new OrQuery(queries); }

  public static Query any(List<Query> queries) { return any(queries.toArray(new Query[]{})); }

  public static Query all(Query... queries) { return new AndQuery(queries); }

  public static Query all(List<Query> queries) { return all(queries.toArray(new Query[]{})); }

  public static Query has(String key, String value) { return new AttributeQuery(key, value); }

  public static Query isActive() { return has("status", "active"); }

  public static Query isInactive() { return has("status", "inactive"); }

  public abstract Object accept(QueryVisitor visitor);
  public abstract boolean isProfileValid(Profile profile);

  public interface QueryVisitor {
    default Object visit(Query query) { return query.accept(this); }
    Object visit(OrQuery query);
    Object visit(AndQuery query);
    Object visit(AttributeQuery query);
  }
}
