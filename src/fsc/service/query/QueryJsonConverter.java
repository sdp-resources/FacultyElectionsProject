package fsc.service.query;

import fsc.entity.query.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class QueryJsonConverter implements Query.QueryVisitor<JSONObject> {
  public Query fromJSON(JSONObject root) {
    String firstKey = JSONObject.getNames(root)[0];

    switch (firstKey) {
      case "and":
        return Query.all(getQueriesFromJSONArray((JSONArray) root.get("and")));
      case "or":
        return Query.any(getQueriesFromJSONArray((JSONArray) root.get("or")));
      default:
        Object value = root.get(firstKey);
        if (value instanceof String) {
          return Query.has(firstKey, (String) value);
        }
        if ((value instanceof Boolean) && (Boolean) value) {
          return Query.named(firstKey, null);
        }
        throw new RuntimeException("Cannot process value: " + value);
    }
  }

  public JSONObject toJSON(Query query) {
    return visit(query);
  }

  private Query[] getQueriesFromJSONArray(JSONArray array) {
    Query[] queries = new Query[array.length()];

    for (int i = 0; i < array.length(); i++) {
      queries[i] = fromJSON((JSONObject) array.get(i));
    }

    return queries;
  }

  public JSONObject visit(OrQuery query) {
    return new JSONObject().put("or", visit(query.queries));
  }

  public JSONObject visit(AndQuery query) {
    return new JSONObject().put("and", visit(query.queries));
  }

  private JSONArray visit(List<Query> queries) {
    JSONArray jsonQueries = new JSONArray();
    for (Query q : queries) { jsonQueries.put(visit(q)); }
    return jsonQueries;
  }

  public JSONObject visit(AttributeQuery query) {
    return new JSONObject().put(query.key, query.value);
  }

  public JSONObject visit(NotQuery query) {
    throw new RuntimeException("Need to handle this case");
  }

  public JSONObject visit(NamedQuery query) {
    return new JSONObject().put(query.name, true);
  }
}
