package fsc.gateway;

import fsc.entity.query.NamedQuery;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;

import java.util.Map;

public interface QueryGateway {
  void addQuery(String name, Query query);
  boolean hasQuery(String name);
  Query getQuery(String name);
  NamedQuery getNamedQuery(String name) throws UnknownQueryNameException;
  QueryValidationResult validateQueryString(String queryString);
  void save();
  Map<String, Query> getAllQueries();

  class UnknownQueryNameException extends RuntimeException {}
}
