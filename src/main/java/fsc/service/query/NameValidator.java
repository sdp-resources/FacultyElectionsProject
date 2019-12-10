package fsc.service.query;

import fsc.entity.query.Query;

public interface NameValidator {
  boolean isValidValueForKey(String key, String value);
  boolean hasQueryNamed(String name);
  Query getQueryNamed(String name);
}
