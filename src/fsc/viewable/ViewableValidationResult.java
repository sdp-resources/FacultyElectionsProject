package fsc.viewable;

import fsc.entity.query.QueryValidationResult;
import fsc.entity.query.QueryValidationResult.InvalidQueryResult;
import fsc.entity.query.QueryValidationResult.ValidQueryResult;

import java.util.Objects;

public class ViewableValidationResult {

  public final boolean isValid;
  public final String message;
  public final String expandedString;

  public ViewableValidationResult(QueryValidationResult result) {
    if (result instanceof ValidQueryResult) {
      this.isValid = true;
      this.message = ((ValidQueryResult) result).normalizedQueryString;
      this.expandedString = ((ValidQueryResult) result).expandedQueryString;
    } else if (result  instanceof InvalidQueryResult) {
      this.isValid = false;
      this.message = ((InvalidQueryResult) result).errorMessage;
      this.expandedString = null;
    } else {
      throw new RuntimeException("A QueryValidationResult must be one of two kinds");
    }
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewableValidationResult that = (ViewableValidationResult) o;
    return isValid == that.isValid &&
                 Objects.equals(message, that.message);
  }

  public int hashCode() {
    return Objects.hash(isValid, message);
  }
}
