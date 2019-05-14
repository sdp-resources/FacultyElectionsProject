package fsc.entity;

import fsc.entity.query.Query;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class QueryGeneratorTest {
  @Test
  public void ProfileWithNameJoeIsValidWithQueryAskingForJoe()
  {
    QueryGenerator gen = new QueryGenerator();

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", "Joe");

    Profile profile = new Profile("Joe", "asd", "sfdgds", "asdasd");

    Query query = gen.generate(jsonObject.toString());

    assertTrue(query.isProfileValid(profile));
  }

  @Test
  public void ProfileNameWithNameJaneIsNOTValidWithQueryAskingForJoe()
  {
    QueryGenerator gen = new QueryGenerator();

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", "Joe");

    Profile profile = new Profile("Jane", "asd", "sfdgds", "asdasd");

    Query query = gen.generate(jsonObject.toString());

    assertFalse(query.isProfileValid(profile));
  }
}
