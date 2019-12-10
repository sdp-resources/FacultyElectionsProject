package fsc.service.query;

import fsc.entity.query.Query;

public class AcceptingNameValidator implements NameValidator {
  public boolean isValidValueForKey(String key, String value) {
    return true;
  }

  public boolean hasQueryNamed(String name) {
    return true;
  }

  public Query getQueryNamed(String name) {
    return null;
  }
}
