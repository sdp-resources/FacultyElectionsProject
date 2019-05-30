package fsc.service;

import fsc.entity.query.Query;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryJsonConverterTest {

  private QueryJsonConverter queryJsonConverter;

  @Before
  public void setUp() {
    queryJsonConverter = new QueryJsonConverter();
  }

  @Test
  public void queryAskingForNameOnlyPicksUpCorrectName() {
    jsonProducesQuery(
          "{ name: \"Joe\" }",
          Query.has("name", "Joe"));
    jsonProducesQuery(
          "{ and: [" +
                "{ division: \"Art\" }," +
                "{ contract: \"tenured\" }" +
                "]}",
          Query.all(Query.has("division", "Art"),
                    Query.has("contract", "tenured")));
    jsonProducesQuery(
          "{ or: [" +
                "{ division: \"Art\" }," +
                "{ contract: \"tenured\" }" +
                "]}",
          Query.any(Query.has("division", "Art"),
                    Query.has("contract", "tenured")));
    jsonProducesQuery(
          "{ and: [" +
                "{ contract: \"tenured\" }," +
                "{ or: [" +
                "   { division: \"Art\" }," +
                "   { division: \"Librarian\" }" +
                "]}" +
                "]}",
          Query.all(
                Query.has("contract", "tenured"),
                Query.any(Query.has("division", "Art"),
                          Query.has("division", "Librarian"))));
  }

  private void jsonProducesQuery(String json, Query expectedQuery) {
    assertEquals(expectedQuery, queryJsonConverter.fromJSON(new JSONObject(json)));
    assertEquals(new JSONObject(json).toString(), queryJsonConverter.toJSON(expectedQuery).toString());
  }
}
