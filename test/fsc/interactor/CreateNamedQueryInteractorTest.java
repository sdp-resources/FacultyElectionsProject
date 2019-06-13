package fsc.interactor;

import fsc.entity.query.NamedQuery;
import fsc.entity.query.Query;
import fsc.mock.gateway.query.ProvidingQueryGatewaySpy;
import fsc.request.CreateNamedQueryRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateNamedQueryInteractorTest {

  public static final String QUERY_NAME = "isActive";
  public static final String QUERY_STRING = "status equals \"active\"";
  private QueryInteractor interactor;
  private ProvidingQueryGatewaySpy gateway;
  private CreateNamedQueryRequest request;
  private Query actualQuery;
  private NamedQuery namedQuery;

  @Before
  public void setUp() throws QueryStringParser.QueryParseException {
    request = new CreateNamedQueryRequest(QUERY_NAME, QUERY_STRING);
    actualQuery = new QueryStringConverter().fromString(QUERY_STRING);
    namedQuery = (NamedQuery) Query.named(QUERY_NAME, actualQuery);
    gateway = new ProvidingQueryGatewaySpy();
    interactor = new QueryInteractor(gateway);
  }

  @Test
  public void whenQueryNameAlreadyExists_produceError() {
    gateway.addQuery(namedQuery.name, namedQuery.query);
    Response response = interactor.handle(request);
    assertEquals(ErrorResponse.resourceExists(), response);
    assertEquals(QUERY_NAME, gateway.requestedName);
  }

  @Test
  public void whenQueryStringIsInvalid_produceError() {
    String queryString = "invalid (";
    request = new CreateNamedQueryRequest(QUERY_NAME, queryString);
    Response response = interactor.handle(request);
    assertTrue(response instanceof ErrorResponse);
    assertEquals(queryString, gateway.requestedValidationForString);
  }

  @Test
  public void whenQueryStringIsValidAndNameDoesNotExist_createNewNamedQuery() {
    Response response = interactor.handle(request);
    assertEquals(new SuccessResponse(), response);
    assertEquals(QUERY_STRING, gateway.requestedValidationForString);
    assertEquals(namedQuery.query, gateway.addedQuery);
    assertEquals(namedQuery.name, gateway.addedQueryName);
    assertTrue(gateway.hasSaved);
  }
}