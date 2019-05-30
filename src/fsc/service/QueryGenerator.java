package fsc.service;

import fsc.entity.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

public class QueryGenerator {
  public Query generate(JSONObject root) {
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

  private Query[] getQueriesFromJSONArray(JSONArray array) {
    Query[] queries = new Query[array.length()];

    for (int i = 0; i < array.length(); i++) {
      queries[i] = generate((JSONObject) array.get(i));
    }

    return queries;
  }
}
