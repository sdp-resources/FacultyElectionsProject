package fsc.service.query;

import fsc.entity.query.Query;

public class ValidatingQueryStringParser extends SimpleQueryStringParser {
  private NameValidator nameValidator;

  public ValidatingQueryStringParser(String s, NameValidator nameValidator) {
    super(s);
    this.nameValidator = nameValidator;
  }

  protected Query getQueryNamed(String name) {
    return nameValidator.getQueryNamed(name);
  }

  protected boolean hasQueryNamed(String key) {
    return nameValidator.hasQueryNamed(key);
  }

  protected boolean isValidValueForKey(String key, String value) {
    return nameValidator.isValidValueForKey(key, value);
  }

}
