package fsc.mock.gateway.query;

import fsc.entity.query.NamedQuery;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.gateway.QueryGateway;
import fsc.service.query.QueryStringConverter;

import java.util.*;

public class ProvidingQueryGatewaySpy implements QueryGateway {
  private final List<NamedQuery> namedQueries = new ArrayList<>();
  public String requestedName;
  public Query addedQuery;
  public String requestedValidationForString;
  public boolean hasSaved;
  public String addedQueryName;

  public ProvidingQueryGatewaySpy(NamedQuery... queries) {
    Collections.addAll(namedQueries, queries);
  }

  public void addQuery(String name, Query query) {
    addedQueryName = name;
    addedQuery = query;
    namedQueries.add(Query.named(name, query));
  }

  public boolean hasQuery(String name) {
    requestedName = name;
    for (NamedQuery namedQuery : namedQueries) {
      if (namedQuery.name.equals(name)) { return true; }
    }
    return false;
  }

  public Query getQuery(String name) {
    requestedName = name;
    for (NamedQuery namedQuery : namedQueries) {
      if (namedQuery.name.equals(name)) { return namedQuery.query; }
    }
    return null;
  }

  public NamedQuery getNamedQuery(String name) throws UnknownQueryNameException {
    requestedName = name;
    for (NamedQuery namedQuery : namedQueries) {
      if (namedQuery.name.equals(name)) {
        return namedQuery;
      }
    }
    throw new UnknownQueryNameException();
  }

  public QueryValidationResult validateQueryString(String queryString) {
    requestedValidationForString = queryString;
    return new QueryStringConverter().validateQueryString(queryString);
  }

  public void save() {
    if (addedQueryName != null || requestedName != null) { hasSaved = true; }
  }

  // TODO: Need to fix this at some point, should not have to return Map
  public Map<String, Query> getAllQueries() {
    Map<String, Query> queryMap = new HashMap<>();
    for (NamedQuery namedQuery : namedQueries) {
      queryMap.put(namedQuery.name, namedQuery.query);
    }

    return queryMap;
  }
}
