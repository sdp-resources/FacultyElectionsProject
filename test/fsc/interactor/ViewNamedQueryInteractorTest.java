package fsc.interactor;

import fsc.entity.query.NamedQuery;
import fsc.entity.query.Query;
import fsc.mock.gateway.query.ProvidingQueryGatewaySpy;
import fsc.request.ViewNamedQueryListRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ViewNamedQueryInteractorTest {

  public static final String QUERY_NAME = "isActive";
  public static final String QUERY_STRING = "status equals \"active\"";
  private QueryInteractor interactor;
  private ProvidingQueryGatewaySpy gateway;
  private ViewNamedQueryListRequest request;
  private Query actualQuery;
  private NamedQuery namedQuery;

  @Before
  public void setUp() throws QueryStringParser.QueryParseException {
    request = new ViewNamedQueryListRequest();
    actualQuery = new QueryStringConverter().fromString(QUERY_STRING);
    namedQuery = (NamedQuery) Query.named(QUERY_NAME, actualQuery);
    gateway = new ProvidingQueryGatewaySpy();
    gateway.addQuery(namedQuery.name, namedQuery.query);
    interactor = new QueryInteractor(gateway);
  }

  @Test
  public void returnListOfStoredRequests() {
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.ofNamedQueries(Map.of(QUERY_NAME, actualQuery)), response);
  }
}