package fsc.entity.query;

import fsc.entity.Profile;

public abstract class Query {
  public static Query always() { return new TrueQuery(); }
  public static Query never() { return new FalseQuery(); }
  public static Query any(Query... queries) { return new OrQuery(queries); }
  public static Query all(Query... queries) { return new AndQuery(queries); }
  public static Query has(String key, String value) { return new AttributeQuery(key, value); }
  public static Query isActive() { return has("status", "active"); }
  public static Query isInactive() { return has("status", "inactive"); }

  public abstract boolean isProfileValid(Profile profile);

  public abstract String getFormattedString();
}
