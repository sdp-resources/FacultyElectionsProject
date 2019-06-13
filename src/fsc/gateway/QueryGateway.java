package fsc.gateway;

import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;

public interface QueryGateway {
  void addQuery(String name, Query query);
  boolean hasQuery(String name);
  QueryValidationResult validateQueryString(String queryString);
  void save();
}
