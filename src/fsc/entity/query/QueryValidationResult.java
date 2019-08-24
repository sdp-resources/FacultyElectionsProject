package fsc.entity.query;

public interface QueryValidationResult {
  class ValidQueryResult implements QueryValidationResult {
    public String normalizedQueryString;
    public String expandedQueryString;
    public Query query;

    public ValidQueryResult(Query query, String normalizedQueryString, String expandedQueryString) {
      this.query = query;
      this.normalizedQueryString = normalizedQueryString;
      this.expandedQueryString = expandedQueryString;
    }
  }

  class InvalidQueryResult implements QueryValidationResult {
    public String errorMessage;

    public InvalidQueryResult(String message) {
      this.errorMessage = message;
    }
  }
}
