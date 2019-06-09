package fsc.service.query;

import fsc.entity.query.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class QueryJsonConverter implements Query.QueryVisitor {
  public Query fromJSON(JSONObject root) {
    String firstKey = JSONObject.getNames(root)[0];

    switch (firstKey) {
      case "and":
        return Query.all(getQueriesFromJSONArray((JSONArray) root.get("and")));
      case "or":
        return Query.any(getQueriesFromJSONArray((JSONArray) root.get("or")));
      default:
        return Query.has(firstKey, root.getString(firstKey));
    }
  }

  public JSONObject toJSON(Query query) {
    return (JSONObject) visit(query);
  }

  private Query[] getQueriesFromJSONArray(JSONArray array) {
    Query[] queries = new Query[array.length()];

    for (int i = 0; i < array.length(); i++) {
      queries[i] = fromJSON((JSONObject) array.get(i));
    }

    return queries;
  }

  public Object visit(OrQuery query) {
    return new JSONObject().put("or", visit(query.queries));
  }

  public Object visit(AndQuery query) {
    return new JSONObject().put("and", visit(query.queries));
  }

  private JSONArray visit(Query[] queries) {
    JSONArray jsonQueries = new JSONArray();
    for (Query q : queries) { jsonQueries.put(visit(q)); }
    return jsonQueries;
  }

  public Object visit(AttributeQuery query) {
    return new JSONObject().put(query.key, query.value);
  }
}
