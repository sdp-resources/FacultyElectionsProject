package fsc.interactor;

import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult.ValidQueryResult;
import fsc.mock.gateway.query.ProvidingQueryGatewaySpy;
import fsc.request.QueryValidationRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.response.ViewResponse;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;
import fsc.viewable.ViewableValidationResult;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueryValidationTest {

  private QueryStringConverter converter;
  private ProvidingQueryGatewaySpy gateway;
  private QueryInteractor interactor;

  @Before
  public void setUp() {
    gateway = new ProvidingQueryGatewaySpy();
    interactor = new QueryInteractor(gateway);
    converter = new QueryStringConverter();
  }

  @Test
  public void validQueriesReturnOkResponse() throws QueryStringParser.QueryParseException {
    assertValidQueryReturnsValidResult("division equals \"Natural Sciences\"");
    assertValidQueryReturnsValidResult("contract equals \"tenure-track\" or " +
                                             "division equals \"Natural Sciences\"");
    assertValidQueryReturnsValidResult("isActive");
  }

  private void assertValidQueryReturnsValidResult(String queryString)
        throws QueryStringParser.QueryParseException {
    QueryValidationRequest request = new QueryValidationRequest(queryString);
    Response response = interactor.execute(request);
    Query query = converter.fromString(queryString);
    assertEquals(ResponseFactory.ofQueryResult(
          new ValidQueryResult(query, converter.toString(query))),
                 response);
    assertTrue(((ViewResponse<ViewableValidationResult>) response).getValues().isValid);
  }
}
