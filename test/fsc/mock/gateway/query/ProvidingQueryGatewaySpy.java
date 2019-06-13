package fsc.mock.gateway.query;

import fsc.entity.query.NamedQuery;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.gateway.QueryGateway;
import fsc.service.query.QueryStringConverter;

import java.util.HashMap;
import java.util.Map;

public class ProvidingQueryGatewaySpy implements QueryGateway {
  private final Map<String, Query> namedQueries = new HashMap<>();
  public String requestedName;
  public Query addedQuery;
  public String requestedValidationForString;
  public boolean hasSaved;
  public String addedQueryName;

  public ProvidingQueryGatewaySpy(Query... queries) {
    for (Query query : queries) {
      NamedQuery namedQuery = (NamedQuery) query;
      addQuery(namedQuery.name, namedQuery.query);
    }
  }

  public void addQuery(String name, Query query) {
    addedQueryName = name;
    addedQuery = query;
    namedQueries.put(name, query);
  }

  public boolean hasQuery(String name) {
    requestedName = name;
    return namedQueries.containsKey(name);
  }

  public QueryValidationResult validateQueryString(String queryString) {
    requestedValidationForString = queryString;
    return new QueryStringConverter().validateQueryString(queryString);
  }

  public void save() {
    if (addedQueryName != null) { hasSaved = true; }
  }
}
