package fsc.viewable;

import fsc.entity.query.QueryValidationResult;
import fsc.entity.query.QueryValidationResult.InvalidQueryResult;
import fsc.entity.query.QueryValidationResult.ValidQueryResult;

public class ViewableValidationResult {

  public final boolean isValid;
  public final String message;

  public ViewableValidationResult(QueryValidationResult result) {
    if (result instanceof ValidQueryResult) {
      this.isValid = true;
      this.message = ((ValidQueryResult) result).normalizedQueryString;
    } else if (result  instanceof InvalidQueryResult) {
      this.isValid = false;
      this.message = ((InvalidQueryResult) result).errorMessage;
    } else {
      throw new RuntimeException("A QueryValidationResult must be one of two kinds");
    }
  }
}
