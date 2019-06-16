package fixtures;

import fsc.viewable.ViewableValidationResult;

public class StringValidation {
  private ViewableValidationResult validationResult;

  public StringValidation() {
  }

  public void setString(String string) {
    validationResult = TestContext.app.validateString(string);
  }

  public String isValid() {
    return validationResult.isValid ? "yes" : "no";
  }

  public String errorOrResult() {
    return validationResult.message;
  }
}

