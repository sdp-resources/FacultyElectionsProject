package fsc.interactor;

import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.entity.query.QueryValidationResult.InvalidQueryResult;
import fsc.entity.query.QueryValidationResult.ValidQueryResult;
import fsc.gateway.QueryGateway;
import fsc.request.CreateNamedQueryRequest;
import fsc.request.ViewNamedQueryListRequest;
import fsc.response.*;

import java.util.Map;

public class QueryInteractor extends Interactor {
  private QueryGateway gateway;

  public QueryInteractor(QueryGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(CreateNamedQueryRequest request) {
    if (gateway.hasQuery(request.name)) {
      return ErrorResponse.resourceExists();
    }
    QueryValidationResult validationResult = gateway.validateQueryString(request.queryString);
    if (validationResult instanceof InvalidQueryResult) {
      String errorMessage = ((InvalidQueryResult) validationResult).errorMessage;
      return ErrorResponse.invalidQuery(errorMessage);
    }
    Query validatedQuery = ((ValidQueryResult) validationResult).query;
    gateway.addQuery(request.name, validatedQuery);
    gateway.save();
    return new SuccessResponse();
  }

  public Response execute(ViewNamedQueryListRequest request) {
    Map<String, Query> queries = gateway.getAllQueries();
    return ViewResponse.ofNamedQueries(queries);
  }
}
