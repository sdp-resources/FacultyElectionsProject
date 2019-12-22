package fsc.service.query;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import fsc.entity.query.Query;
import fsc.gateway.QueryGateway;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GatewayBackedNameValidatorTest {
  private QueryStringConverter converter;
  private NameValidatorSpy validator;

  @Before
  public void setUp() {
    validator = new NameValidatorSpy();
    converter = new QueryStringConverter(new ValidatingQueryStringParserFactory(validator));
    validator.addValidValue("status", "active");
    validator.addValidValue("status", "inactive");
  }

  @Test
  public void whenAskedForNamedQuery_asksTheGateway()
        throws QueryStringParser.QueryParseException {
    Query expectedQuery = converter.fromString("status is active");
    validator.addNamedQuery("isActive", expectedQuery);
    assertTrue(validator.hasQueryNamed("isActive"));
    assertEquals(expectedQuery, validator.getQueryNamed("isActive"));
    assertFalse(validator.hasQueryNamed("isInactive"));
    try {
      validator.getQueryNamed("isInactive");
      fail();
    } catch (QueryGateway.UnknownQueryNameException ignored) {}
  }

  @Test
  public void whenAskedForDivision_asksTheGateway() {
    validator.addValidValue("division", "Natural Sciences");
    assertTrue(validator.isValidValueForKey("division", "Natural Sciences"));
    assertFalse(validator.isValidValueForKey("division", "Nat Sciences"));
  }

  @Test
  public void whenAskedForContractType_asksTheGateway() {
    validator.addValidValue("contract", "tenure-track");
    assertTrue(validator.isValidValueForKey("contract", "tenure-track"));
    assertFalse(validator.isValidValueForKey("contract", "something"));
  }

  private class NameValidatorSpy implements NameValidator {
    Multimap<String, String> validValues = ArrayListMultimap.create();
    private Map<String, Query> namedQueries = new HashMap<>();

    void addValidValue(String key, String value) {
      validValues.put(key, value);
    }

    public void addNamedQuery(String name, Query query) {
      namedQueries.put(name, query);
    }

    public boolean isValidValueForKey(String key, String value) {
      return validValues.containsEntry(key, value);
    }

    public boolean hasQueryNamed(String name) {
      return namedQueries.containsKey(name);
    }

    public Query getQueryNamed(String name) {
      if (hasQueryNamed(name)) {
        return namedQueries.get(name);
      }
      throw new QueryGateway.UnknownQueryNameException();
    }
  }
}