package fsc.entity;

import fsc.entity.query.AlwaysTrueQuery;
import fsc.entity.query.AttributeQuery;
import fsc.entity.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class QueryGenerator {
  public Query generate(JSONObject root) {
    String firstKey = JSONObject.getNames(root)[0];

    switch (firstKey)
    {
      case "and":
        return new AndQuery(getQueriesFromJSONArray((JSONArray) root.get("and")));
      case "or":
        return new OrQuery(getQueriesFromJSONArray((JSONArray) root.get("or")));
      default:
        return new AttributeQuery(firstKey, root.getString(firstKey));
    }
  }

  private Query[] getQueriesFromJSONArray(JSONArray array) {
    Query[] queries = new Query[array.length()];

    for(int i = 0; i < array.length(); i++)
    {
      queries[i] = generate((JSONObject) array.get(i));
    }

    return queries;
  }
}
