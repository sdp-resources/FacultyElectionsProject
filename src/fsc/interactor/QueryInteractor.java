package fsc.interactor;

import fsc.entity.query.NamedQuery;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.entity.query.QueryValidationResult.InvalidQueryResult;
import fsc.entity.query.QueryValidationResult.ValidQueryResult;
import fsc.gateway.QueryGateway;
import fsc.request.*;
import fsc.response.Response;
import fsc.response.ResponseFactory;

import java.util.Map;

public class QueryInteractor extends Interactor {
  private QueryGateway gateway;

  public QueryInteractor(QueryGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(CreateNamedQueryRequest request) {
    if (gateway.hasQuery(request.name)) {
      return ResponseFactory.resourceExists();
    }
    QueryValidationResult validationResult = gateway.validateQueryString(request.queryString);
    if (validationResult instanceof InvalidQueryResult) {
      String errorMessage = ((InvalidQueryResult) validationResult).errorMessage;
      return ResponseFactory.invalidQuery(errorMessage);
    }
    Query validatedQuery = ((ValidQueryResult) validationResult).query;
    gateway.addQuery(request.name, validatedQuery);
    gateway.save();
    return ResponseFactory.success();
  }

  public Response execute(EditNamedQueryRequest request) {
    // TODO: At some point move away from checking for null and use fetcher
    // TODO: This cast should not be needed. gateway should return NamedQuery
    try {
      NamedQuery storedQuery = gateway.getNamedQuery(request.name);
      // TODO this validation is  begging to be "fetched" as well
      QueryValidationResult validationResult = gateway.validateQueryString(request.queryString);
      if (validationResult instanceof InvalidQueryResult) {
        String errorMessage = ((InvalidQueryResult) validationResult).errorMessage;
        return ResponseFactory.invalidQuery(errorMessage);
      }

      storedQuery.query = ((ValidQueryResult) validationResult).query;
      gateway.save();
      return ResponseFactory.success();
    } catch (QueryGateway.UnknownQueryNameException e) {
      return ResponseFactory.invalidQueryName();
    }
  }

  public Response execute(ViewNamedQueryListRequest request) {
    Map<String, Query> queries = gateway.getAllQueries();
    return ResponseFactory.ofNamedQueries(queries);
  }

  public Response execute(QueryValidationRequest request) {
    QueryValidationResult result = gateway.validateQueryString(request.queryString);
    return ResponseFactory.ofQueryResult(result);
  }
}
