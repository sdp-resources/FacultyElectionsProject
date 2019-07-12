package fsc.service.query;

import fsc.entity.EntityFactory;
import fsc.entity.query.Query;
import fsc.gateway.Gateway;
import fsc.gateway.QueryGateway;
import gateway.InMemoryGateway;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GatewayBackedNameValidatorTest {
  private Gateway gateway;
  private QueryStringConverter converter;
  private NameValidator validator;
  private EntityFactory entityFactory;

  @Before
  public void setUp() {
    gateway = new InMemoryGateway();
    converter = new QueryStringConverter();
    validator = new GatewayBackedQueryValidator(gateway);
    entityFactory = gateway.getEntityFactory();
  }

  @Test
  public void whenAskedForNamedQuery_asksTheGateway()
        throws QueryStringParser.QueryParseException {
    Query expectedQuery = converter.fromString("status is active");
    gateway.addQuery("isActive", expectedQuery);
    assertTrue(validator.hasQueryNamed("isActive"));
    assertEquals(expectedQuery, validator.getQueryNamed("isActive"));
    assertFalse(validator.hasQueryNamed("isInactive"));
    try {
      validator.getQueryNamed("isInactive");
      fail();
    } catch (QueryGateway.UnknownQueryNameException e) {}
  }

  @Test
  public void whenAskedForDivision_asksTheGateway() {
    gateway.addDivision(entityFactory.createDivision("Natural Sciences"));
    assertTrue(validator.isValidValueForKey("division", "Natural Sciences"));
    assertFalse(validator.isValidValueForKey("division", "Nat Sciences"));
  }

  @Test
  public void whenAskedForContractType_asksTheGateway() {
    gateway.addContractType(entityFactory.createContractType("tenure-track"));
    assertTrue(validator.isValidValueForKey("contract", "tenure-track"));
    assertFalse(validator.isValidValueForKey("contract", "something"));
  }
}