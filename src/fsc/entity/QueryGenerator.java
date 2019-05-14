package fsc.entity;

import fsc.entity.query.AttributeQuery;
import fsc.entity.query.Query;
import org.json.JSONObject;
import org.json.JSONTokener;

public class QueryGenerator {
  public Query generate(String jsonString) {
    JSONObject object = new JSONObject(jsonString);
    String[] keys = JSONObject.getNames(object);

    return new AttributeQuery(keys[0], object.getString(keys[0]));
  }
}
