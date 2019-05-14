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
        Query[] queries = getQueriesFromJSONArray((JSONArray) root.get("and"));

        return new AndQuery(queries);
      case "or":
        break;
      default:
        return new AttributeQuery(firstKey, root.getString(firstKey));
    }
    return new AlwaysTrueQuery();

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
