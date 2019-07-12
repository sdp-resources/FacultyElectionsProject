package dbGateway;

import fsc.entity.ContractType;
import fsc.entity.Division;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.service.query.*;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DatabaseNamedQueryTest extends BasicDatabaseTest {
  private static final String IS_TENURED = "isTenured";
  public static final String IS_NAT_SCI = "isNaturalSciences";
  private Query query;
  private Query divisionQuery;

  @Test
  public void canCreateANamedQuery() {
    saveTheQuery();
    Map<String, Query> namedQueries = anotherGateway.getAllQueries();
    assertEquals(1, namedQueries.size());
    assertEquals(query, namedQueries.get(IS_TENURED));
    assertEquals(true, anotherGateway.hasQuery(IS_TENURED));
    assertEquals(query, anotherGateway.getQuery(IS_TENURED));
  }

  @Test
  public void savedQueriesCanBeReferredToInOtherQueries()
        throws QueryStringParser.QueryParseException {
    saveBothQueries();
    NameValidator oldValidator = QueryStringParser.getNameValidator();
    QueryStringParser.setNameValidator(new GatewayBackedQueryValidator(gateway));
    QueryStringConverter converter = new QueryStringConverter();
    Query resultQuery = converter.fromString(IS_TENURED + " and " + IS_NAT_SCI);
    QueryStringParser.setNameValidator(oldValidator);
    assertEquals(Query.all(Query.named(IS_TENURED, query),
                           Query.named(IS_NAT_SCI, divisionQuery)),
                 resultQuery);
  }

  @Test
  public void canGetCorrectValidationResult() {
    saveBothQueries();
    QueryValidationResult validationResult = anotherGateway.validateQueryString(
          IS_TENURED + " and " + IS_NAT_SCI);
    assertTrue(validationResult instanceof QueryValidationResult.ValidQueryResult);
    assertEquals(Query.all(Query.named(IS_TENURED, query),
                           Query.named(IS_NAT_SCI, divisionQuery)),
                 ((QueryValidationResult.ValidQueryResult) validationResult).query);

  }

  private void saveTheQuery() {
    query = Query.has("contract", "tenured");
    gateway.addQuery(IS_TENURED, query);
    gateway.commit();
  }

  private void saveBothQueries() {
    gateway.addContractType(new ContractType("tenured"));
    query = Query.has("contract", "tenured");
    gateway.addQuery(IS_TENURED, query);
    gateway.addDivision(new Division("Natural Sciences"));
    divisionQuery = Query.has("division", "Natural Sciences");
    gateway.addQuery(IS_NAT_SCI, divisionQuery);
    gateway.commit();
  }

}
