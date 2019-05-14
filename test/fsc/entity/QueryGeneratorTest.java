package fsc.entity;

import fsc.entity.query.Query;
import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


public class QueryGeneratorTest {

  private QueryGenerator queryGenerator;
  private Profile joeProfile;
  private Profile janeProfile;
  private Profile sallyProfile;
  private Profile samProfile;

  @Before
  public void setUp() throws Exception {
    queryGenerator = new QueryGenerator();
    joeProfile = new Profile("Joe", "joe@hanover.edu", "Art", "tenured");
    janeProfile = new Profile("Jane", "jane@hanover.edu", "Librarian", "non-tenured");
    sallyProfile = new Profile("Sally", "sally@hanover.edu", "Librarian", "tenured");
    samProfile = new Profile("Sam", "sam@hanover.edu", "Art", "non-tenured");
  }

  @Test
  public void profileWithNameJoeIsValidWithQueryAskingForJoe()
  {

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", "Joe");

    Query query = queryGenerator.generate(jsonObject);

    assertTrue(query.isProfileValid(joeProfile));
  }

  @Test
  public void profileNameWithNameJaneIsNOTValidWithQueryAskingForJoe()
  {

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", "Joe");

    Query query = queryGenerator.generate(jsonObject);

    assertFalse(query.isProfileValid(janeProfile));
  }

  @Test
  public void queryRequiresArtAndTenuredExpectOnlyJoe()
  {
    JSONObject and = new JSONObject();
    JSONObject department = new JSONObject();
    department.put("department", "Art");
    JSONObject contract = new JSONObject();
    contract.put("contract", "tenured");

    and.put("and", new JSONArray( new JSONObject[] { department, contract }));

    Query query = queryGenerator.generate(and);

    assertTrue(query.isProfileValid(joeProfile));
    assertFalse(query.isProfileValid(janeProfile));
    assertFalse(query.isProfileValid(sallyProfile));
    assertFalse(query.isProfileValid(samProfile));
  }

  @Test
  public void queryRequiresArtOrTenuredExpectJoeSallyAndSam()
  {
    JSONObject or = new JSONObject();
    JSONObject department = new JSONObject();
    department.put("department", "Art");
    JSONObject contract = new JSONObject();
    contract.put("contract", "tenured");

    or.put("or", new JSONArray( new JSONObject[] { department, contract }));

    Query query = queryGenerator.generate(or);

    assertTrue(query.isProfileValid(joeProfile));
    assertFalse(query.isProfileValid(janeProfile));
    assertTrue(query.isProfileValid(sallyProfile));
    assertTrue(query.isProfileValid(samProfile));
  }

  @Test
  public void queryRequiresTenured_And_ArtOrLibrarian()
  {
    JSONObject contract = new JSONObject();
    contract.put("contract", "tenured");

    JSONObject art = new JSONObject();
    art.put("department", "Art");
    JSONObject librarian = new JSONObject();
    librarian.put("department", "Librarian");

    JSONObject or = new JSONObject();
    or.put("or", new JSONArray( new JSONObject[] { art, librarian } ));

    JSONObject and = new JSONObject();
    and.put("and", new JSONArray( new JSONObject[] { contract, or }));

    Query query = queryGenerator.generate(and);

    assertTrue(query.isProfileValid(joeProfile));
    assertFalse(query.isProfileValid(janeProfile));
    assertTrue(query.isProfileValid(sallyProfile));
    assertFalse(query.isProfileValid(samProfile));
  }
}
