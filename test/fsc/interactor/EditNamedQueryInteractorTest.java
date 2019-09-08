package fsc.interactor;

import fsc.entity.query.NamedQuery;
import fsc.entity.query.Query;
import fsc.mock.gateway.query.ProvidingQueryGatewaySpy;
import fsc.request.EditNamedQueryRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EditNamedQueryInteractorTest {

  private static final String QUERY_NAME = "isActive";
  private static final String QUERY_STRING = "status equals \"active\"";
  private QueryInteractor interactor;
  private ProvidingQueryGatewaySpy gateway;
  private EditNamedQueryRequest request;
  private Query actualQuery;
  private NamedQuery namedQuery;

  @Before
  public void setUp() throws QueryStringParser.QueryParseException {
    request = new EditNamedQueryRequest(QUERY_NAME, QUERY_STRING);
    actualQuery = new QueryStringConverter().fromString(QUERY_STRING);
    namedQuery = Query.named(QUERY_NAME, actualQuery);
    gateway = new ProvidingQueryGatewaySpy();
    interactor = new QueryInteractor(gateway);
  }

  @Test
  public void whenQueryNameDoesNotExist_produceError() {
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.invalidQueryName(), response);
    assertEquals(QUERY_NAME, gateway.requestedName);
  }

  @Test
  public void whenQueryStringIsInvalid_produceError() {
    namedQuery = Query.named(QUERY_NAME, Query.always());
    gateway = new ProvidingQueryGatewaySpy(namedQuery);
    interactor = new QueryInteractor(gateway);
    String queryString = "invalid (";
    request = new EditNamedQueryRequest(QUERY_NAME, queryString);
    Response response = interactor.handle(request);
    assertTrue(response instanceof ErrorResponse);
    assertEquals(queryString, gateway.requestedValidationForString);
  }

  @Test
  public void whenQueryStringIsValidAndNameExists_changeStoredQuery() {
    namedQuery = Query.named(QUERY_NAME, Query.always());
    gateway = new ProvidingQueryGatewaySpy(namedQuery);
    interactor = new QueryInteractor(gateway);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.success(), response);
    assertEquals(QUERY_STRING, gateway.requestedValidationForString);
    assertEquals(Query.has("status", "active"), namedQuery.query);
    assertEquals(namedQuery.name, gateway.requestedName);
    assertTrue(gateway.hasSaved);
  }
}